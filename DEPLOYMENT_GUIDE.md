# Deployment Guide - University Department System

This guide provides step-by-step instructions for deploying your full-stack application to cloud services.

## Deployment Architecture

- **Database**: Neon.tech (PostgreSQL)
- **Backend**: Render.com (Spring Boot)
- **Frontend**: Netlify.com (React)

## Prerequisites

- GitHub account with your project repository
- Accounts on:
  - [Neon.tech](https://neon.tech)
  - [Render.com](https://render.com)
  - [Netlify.com](https://netlify.com)

---

## Step 1: Deploy PostgreSQL Database on Neon.tech

### 1.1 Create a Neon Project

1. Go to [neon.tech](https://neon.tech) and sign up/login
2. Click "New Project"
3. Fill in project details:
   - **Project Name**: university-department-system
   - **Region**: Choose closest to you
   - **PostgreSQL Version**: Latest (15 or 16)
4. Click "Create Project"

### 1.2 Get Database Connection Details

After project creation, you'll see a connection string like:
```
postgresql://username:password@ep-xxx-xxx.region.aws.neon.tech/dbname
```

Save these details:
- **Full Connection URL**
- **Username**
- **Password**
- **Host**
- **Database Name**

### 1.3 Test Connection (Optional)

You can test the connection using a PostgreSQL client or update your local `application-prod.properties` to point to Neon.

---

## Step 2: Deploy Backend on Render.com

### 2.1 Create Web Service

1. Go to [render.com](https://render.com) and login
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Select your `UniversityDepartmentSystem` repository

### 2.2 Configure Service

Fill in the following settings:

- **Name**: `university-department-backend` (or your choice)
- **Region**: Choose closest to you
- **Branch**: `main` (or your default branch)
- **Root Directory**: Leave empty (project root)
- **Environment**: `Java`
- **Build Command**:
  ```
  ./gradlew build -x test
  ```
- **Start Command**:
  ```
  java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar build/libs/*.jar
  ```

### 2.3 Set Environment Variables

Click "Advanced" → "Add Environment Variable" and add:

| Key | Value |
|-----|-------|
| `DATABASE_URL` | Your Neon PostgreSQL connection URL |
| `DB_USERNAME` | Your Neon database username |
| `DB_PASSWORD` | Your Neon database password |
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `FRONTEND_URL` | Will be added after frontend deployment |

### 2.4 Deploy

1. Click "Create Web Service"
2. Wait for deployment (5-10 minutes)
3. Once deployed, you'll get a URL like: `https://university-department-backend.onrender.com`
4. **Save this backend URL** - you'll need it for frontend configuration

### 2.5 Test Backend

Test your backend is working:
```bash
curl https://your-backend-url.onrender.com/api/departments
```

You should see JSON response with departments.

---

## Step 3: Deploy Frontend on Netlify.com

### 3.1 Update Frontend API Configuration

Before deploying, update your React app to use the production backend URL:

1. Create `frontend/.env.production`:
```
REACT_APP_API_URL=https://your-backend-url.onrender.com
```

2. Update `frontend/src/services/api.js` to use environment variable:
```javascript
import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: `${API_BASE_URL}/api`
});

// ... rest of your API configuration
```

3. Commit and push these changes to GitHub

### 3.2 Create Netlify Site

1. Go to [netlify.com](https://netlify.com) and login
2. Click "Add new site" → "Import an existing project"
3. Choose "GitHub" and authorize Netlify
4. Select your `UniversityDepartmentSystem` repository

### 3.3 Configure Build Settings

- **Branch to deploy**: `main`
- **Base directory**: `frontend`
- **Build command**: `npm run build`
- **Publish directory**: `frontend/build`

### 3.4 Set Environment Variables

Go to "Site settings" → "Build & deploy" → "Environment variables" and add:

| Key | Value |
|-----|-------|
| `REACT_APP_API_URL` | Your Render backend URL (without /api) |

### 3.5 Deploy

1. Click "Deploy site"
2. Wait for deployment (2-5 minutes)
3. Once deployed, you'll get a URL like: `https://random-name-123.netlify.app`
4. You can customize this under "Site settings" → "Change site name"

### 3.6 Update Backend CORS

Go back to Render.com and update the `FRONTEND_URL` environment variable with your Netlify URL:

1. Go to your Render service
2. Environment → Add/Edit `FRONTEND_URL`
3. Set value to: `https://your-app-name.netlify.app`
4. Save and wait for auto-deploy

---

## Step 4: Verify Deployment

### 4.1 Check All Services

1. **Database**: Verify connection in Neon dashboard
2. **Backend**: Test API endpoints directly
   ```bash
   curl https://your-backend.onrender.com/api/departments
   curl https://your-backend.onrender.com/api/professors
   ```
3. **Frontend**:
   - Open your Netlify URL in browser
   - Navigate to Departments and Professors pages
   - Test CRUD operations
   - Check browser console for any errors

### 4.2 Test Full Integration

1. Open your Netlify URL
2. Navigate to "Manage Departments"
3. Try to:
   - Search for departments
   - Add a new department
   - Edit a department
   - Delete a department
   - View department details with professors
4. Navigate to "Manage Professors"
5. Try to:
   - Search for professors
   - Add a new professor
   - Edit a professor
   - Delete a professor

---

## Troubleshooting

### Backend Issues

**Problem**: Build fails on Render
- Check `gradlew` has execute permissions
- Verify Java version compatibility
- Check logs in Render dashboard

**Problem**: Backend starts but can't connect to database
- Verify DATABASE_URL is correct
- Check DB_USERNAME and DB_PASSWORD
- Ensure Neon database is active

**Problem**: CORS errors
- Verify FRONTEND_URL matches your Netlify URL exactly
- Check CorsConfig.java allows your frontend origin

### Frontend Issues

**Problem**: Build fails on Netlify
- Check Node version in netlify.toml
- Verify all dependencies in package.json
- Check build logs

**Problem**: API calls fail
- Verify REACT_APP_API_URL is set correctly
- Check browser console for errors
- Verify backend URL is accessible

**Problem**: Blank page after deployment
- Check browser console for errors
- Verify all routes are configured in netlify.toml
- Check if build succeeded

---

## Environment Variables Summary

### Neon (PostgreSQL)
- No environment variables needed - just save connection details

### Render (Backend)
```
DATABASE_URL=postgresql://user:pass@host/dbname
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
SPRING_PROFILES_ACTIVE=prod
FRONTEND_URL=https://your-app.netlify.app
```

### Netlify (Frontend)
```
REACT_APP_API_URL=https://your-backend.onrender.com
```

---

## Deployment URLs Template

Fill this in as you deploy:

```
Database (Neon): ep-xxx-xxx.region.aws.neon.tech
Backend (Render): https://________________.onrender.com
Frontend (Netlify): https://________________.netlify.app
```

---

## Monitoring and Maintenance

### Logs
- **Backend**: Render dashboard → Your service → Logs
- **Frontend**: Netlify dashboard → Your site → Functions → Logs

### Database
- Monitor usage in Neon dashboard
- Free tier has limits - watch your usage

### Updates
- Push code to GitHub main branch
- Render and Netlify auto-deploy on push
- Can disable auto-deploy in dashboard if needed

---

## Free Tier Limitations

### Neon
- Free tier: 0.5 GB storage
- 1 project
- Branches: 10

### Render
- Free tier spins down after 15 mins of inactivity
- First request after spin-down takes ~30-60 seconds
- 750 hours/month free

### Netlify
- 100 GB bandwidth/month
- 300 build minutes/month
- Unlimited sites

---

## Next Steps

1. Test all functionality thoroughly
2. Update README.md with deployment URLs
3. Prepare presentation/demo
4. Document any issues encountered
5. Create user guide if needed

---

## Support

If you encounter issues:
1. Check service status pages
2. Review logs in respective dashboards
3. Consult official documentation:
   - [Neon Docs](https://neon.tech/docs)
   - [Render Docs](https://render.com/docs)
   - [Netlify Docs](https://docs.netlify.com)

---

**Last Updated**: January 2025

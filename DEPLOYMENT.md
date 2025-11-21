# Deployment Guide

This guide explains how to deploy the University Department System to free hosting platforms.

## Architecture Overview

- **Backend**: Spring Boot application (Railway)
- **Frontend**: React application (Vercel)
- **Database**: PostgreSQL (Railway/Supabase)

## Prerequisites

- GitHub account
- Railway account (https://railway.app)
- Vercel account (https://vercel.com)
- Git installed locally

---

## Part 1: Deploy Backend to Railway

### Step 1: Push Code to GitHub

```bash
# Initialize git (if not already done)
git init
git add .
git commit -m "Prepare for deployment"

# Create a new GitHub repository and push
git remote add origin https://github.com/YOUR_USERNAME/UniversityDepartmentSystem.git
git branch -M main
git push -u origin main
```

### Step 2: Deploy to Railway

1. Go to https://railway.app and sign in with GitHub
2. Click **"New Project"**
3. Select **"Deploy from GitHub repo"**
4. Choose your `UniversityDepartmentSystem` repository
5. Railway will automatically detect it's a Java project

### Step 3: Add PostgreSQL Database

1. In your Railway project, click **"+ New"**
2. Select **"Database"** → **"PostgreSQL"**
3. Railway will automatically create a PostgreSQL instance
4. Click on the database service and copy the connection details

### Step 4: Configure Environment Variables

1. Click on your backend service (the Java app)
2. Go to **"Variables"** tab
3. Add the following environment variables:

```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=postgresql://<user>:<password>@<host>:<port>/<database>
DB_USERNAME=<from Railway database>
DB_PASSWORD=<from Railway database>
FRONTEND_URL=https://your-frontend-url.vercel.app
PORT=8080
```

**Note**: Railway provides a `DATABASE_URL` variable automatically if you link the database. You can reference it using `${{Postgres.DATABASE_URL}}`

### Step 5: Deploy

1. Railway will automatically build and deploy
2. Once deployed, click **"Settings"** → **"Generate Domain"** to get a public URL
3. Copy your backend URL (e.g., `https://universitydepartmentsystem-production.up.railway.app`)

---

## Part 2: Deploy Frontend to Vercel

### Step 1: Update Frontend Environment Variables

1. Open `frontend/.env.production`
2. Replace with your actual Railway backend URL:

```env
REACT_APP_API_URL=https://your-backend-url.railway.app/api
```

3. Commit and push changes:

```bash
git add frontend/.env.production
git commit -m "Update backend URL for production"
git push
```

### Step 2: Deploy to Vercel

1. Go to https://vercel.com and sign in with GitHub
2. Click **"Add New Project"**
3. Import your `UniversityDepartmentSystem` repository
4. Configure the project:
   - **Framework Preset**: Create React App
   - **Root Directory**: `frontend`
   - **Build Command**: `npm run build`
   - **Output Directory**: `build`

### Step 3: Add Environment Variables

1. In Vercel project settings, go to **"Environment Variables"**
2. Add:
   - **Key**: `REACT_APP_API_URL`
   - **Value**: `https://your-backend-url.railway.app/api`
   - **Environments**: Production, Preview, Development

### Step 4: Deploy

1. Click **"Deploy"**
2. Vercel will build and deploy your frontend
3. Once complete, you'll get a URL like `https://your-app.vercel.app`

---

## Part 3: Update CORS Settings

### Step 1: Update Backend CORS Configuration

1. Go back to Railway
2. Update the `FRONTEND_URL` environment variable with your Vercel URL:

```
FRONTEND_URL=https://your-app.vercel.app
```

3. Railway will automatically redeploy with the new settings

---

## Part 4: Verify Deployment

### Backend Verification

1. Visit `https://your-backend-url.railway.app/api/departments`
2. You should see JSON data with the seeded departments

### Frontend Verification

1. Visit your Vercel URL (e.g., `https://your-app.vercel.app`)
2. Test all CRUD operations:
   - View departments and professors
   - Create new records
   - Update existing records
   - Delete records

---

## Alternative Deployment Options

### Backend Alternatives

#### Render (Free Tier)
- Sign up at https://render.com
- Similar to Railway but free tier spins down after 15 minutes of inactivity
- Automatically wakes up on request (may take 30-60 seconds)

**Pros**: Generous free tier
**Cons**: Cold starts

#### Heroku (No longer free)
- Previously free, now requires payment

### Frontend Alternatives

#### Netlify
- Sign up at https://netlify.com
- Drag and drop the `frontend/build` folder
- Or connect to GitHub for auto-deployment

**Configuration**:
- Build command: `npm run build`
- Publish directory: `build`
- Environment variable: `REACT_APP_API_URL`

### Database Alternatives

#### Supabase (Free PostgreSQL)
1. Go to https://supabase.com
2. Create a new project
3. Get the database connection string
4. Update Railway environment variables

**Free Tier**: 500MB database, 2GB bandwidth/month

#### Neon (Free PostgreSQL)
1. Go to https://neon.tech
2. Create a new project
3. Get connection string

**Free Tier**: 3GB storage

---

## Troubleshooting

### Backend Issues

**Problem**: Build fails on Railway
```bash
# Solution: Ensure gradlew has execute permissions
chmod +x gradlew
git add gradlew
git commit -m "Make gradlew executable"
git push
```

**Problem**: Database connection fails
- Verify `DATABASE_URL` environment variable is correct
- Check that PostgreSQL dependency is in `build.gradle`
- Ensure `application-prod.properties` is being used

**Problem**: CORS errors
- Verify `FRONTEND_URL` environment variable matches your Vercel URL exactly
- Check browser console for specific error messages

### Frontend Issues

**Problem**: API calls fail
- Verify `REACT_APP_API_URL` in Vercel settings
- Check that backend is running (visit the API URL directly)
- Ensure CORS is configured correctly on backend

**Problem**: 404 on refresh
- Vercel should automatically handle this with `vercel.json`
- If using Netlify, ensure `netlify.toml` is present

**Problem**: Environment variables not working
- Rebuild the project in Vercel after adding variables
- Environment variables must start with `REACT_APP_` prefix
- Restart development server locally after changing `.env` files

---

## Cost Breakdown

### Railway Free Tier
- $5 free credit per month
- ~500 hours of usage
- 1GB RAM, 1 vCPU
- 500MB PostgreSQL database

**Estimated usage**: Should be sufficient for a student project

### Vercel Free Tier
- Unlimited deployments
- 100GB bandwidth/month
- Commercial use allowed for personal projects

**Estimated usage**: More than enough for a student project

### Total Monthly Cost: $0

---

## Continuous Deployment

Both Railway and Vercel support automatic deployments:

1. **Push to GitHub** → Both services automatically rebuild and deploy
2. **Branch Deployments**: Vercel creates preview deployments for pull requests
3. **Rollback**: Both platforms allow rolling back to previous deployments

---

## Security Considerations

1. **Never commit sensitive data**:
   - `.env.production` is in `.gitignore`
   - Use environment variables for all secrets

2. **CORS Configuration**:
   - Only allow your frontend URL
   - Don't use `*` in production

3. **Database**:
   - Use strong passwords
   - Enable SSL connections (Railway/Supabase enable by default)

---

## Monitoring

### Railway
- Built-in logs and metrics
- Monitor CPU, RAM, and network usage
- Set up usage alerts

### Vercel
- Analytics dashboard
- Performance monitoring
- Error tracking

---

## Scaling Considerations

If your project needs to scale beyond free tiers:

1. **Railway**: Upgrade to Pro ($20/month)
2. **Vercel**: Stays free for personal projects
3. **Database**: Upgrade to larger plans as needed

---

## Additional Resources

- [Railway Documentation](https://docs.railway.app)
- [Vercel Documentation](https://vercel.com/docs)
- [Spring Boot Deployment Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html)
- [Create React App Deployment](https://create-react-app.dev/docs/deployment/)

---

## Support

For issues specific to this project:
1. Check the troubleshooting section above
2. Review Railway/Vercel logs
3. Consult your professor or teaching assistant

---

**Last Updated**: October 26, 2025

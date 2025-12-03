# Quick Deployment Guide

## 5-Minute Setup

### 1. Backend (Railway) - 2 minutes

```bash
# Push to GitHub
git add .
git commit -m "Deploy to production"
git push
```

1. Go to https://railway.app → Sign in with GitHub
2. Click "New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Click "+ New" → "Database" → "PostgreSQL"
5. Click backend service → "Variables" → Add:
   ```
   SPRING_PROFILES_ACTIVE=prod
   FRONTEND_URL=https://your-app.vercel.app
   ```
6. Settings → "Generate Domain" → Copy URL

### 2. Frontend (Vercel) - 2 minutes

1. Update `frontend/.env.production`:
   ```
   REACT_APP_API_URL=https://your-railway-url.railway.app/api
   ```

2. Push changes:
   ```bash
   git add .
   git commit -m "Update API URL"
   git push
   ```

3. Go to https://vercel.com → Sign in with GitHub
4. Click "Add New Project" → Import your repo
5. Configure:
   - Root Directory: `frontend`
   - Framework: Create React App
6. Add Environment Variable:
   - Key: `REACT_APP_API_URL`
   - Value: `https://your-railway-url.railway.app/api`
7. Click "Deploy"

### 3. Update CORS - 1 minute

1. Go back to Railway
2. Update `FRONTEND_URL` variable to your Vercel URL
3. Wait for automatic redeployment

### Done!

Visit your Vercel URL and start using the app!

---

## Important URLs to Save

- **Frontend**: https://________.vercel.app
- **Backend API**: https://________.railway.app/api
- **Railway Dashboard**: https://railway.app/project/________
- **Vercel Dashboard**: https://vercel.com/________/universitydepartmentsystem

---

## Quick Troubleshooting

**CORS Error**:
- Update `FRONTEND_URL` in Railway to match Vercel URL exactly

**API Not Found**:
- Check `REACT_APP_API_URL` in Vercel environment variables
- Ensure it ends with `/api`

**Database Not Working**:
- Verify PostgreSQL is linked in Railway
- Check backend logs in Railway dashboard

---

## Deployment Checklist

- [ ] Code pushed to GitHub
- [ ] Railway project created
- [ ] PostgreSQL database added to Railway
- [ ] Backend environment variables set (`SPRING_PROFILES_ACTIVE`, `FRONTEND_URL`)
- [ ] Backend domain generated
- [ ] Frontend `.env.production` updated with backend URL
- [ ] Changes committed and pushed
- [ ] Vercel project created
- [ ] Frontend environment variables set (`REACT_APP_API_URL`)
- [ ] Frontend deployed successfully
- [ ] CORS updated with frontend URL
- [ ] Tested all CRUD operations
- [ ] URLs documented for submission

---

## Commands Reference

```bash
# Build backend locally
./gradlew build

# Build frontend locally
cd frontend
npm install
npm run build

# Test locally before deploying
./gradlew bootRun  # Backend on :8080
npm start          # Frontend on :3000

# Deploy
git add .
git commit -m "Your message"
git push  # Triggers auto-deployment on both platforms
```

---

For detailed instructions, see [DEPLOYMENT.md](DEPLOYMENT.md)

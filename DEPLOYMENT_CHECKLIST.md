# Deployment Checklist for Submission

Use this checklist to ensure your deployment is complete before submitting to your teacher.

## Pre-Deployment Checklist

### Code Preparation
- [ ] All code is committed to local Git repository
- [ ] Application runs successfully locally (backend on :8080, frontend on :3000)
- [ ] All CRUD operations work locally
- [ ] No errors in browser console or backend logs
- [ ] README.md is up to date

### GitHub Setup
- [ ] GitHub account created
- [ ] New repository created on GitHub
- [ ] Code pushed to GitHub repository
- [ ] Repository is public (or teacher has access)

## Backend Deployment (Railway)

### Railway Account Setup
- [ ] Created account at https://railway.app
- [ ] Signed in with GitHub
- [ ] Email verified (if required)

### Backend Deployment Steps
- [ ] Created new Railway project
- [ ] Connected to GitHub repository
- [ ] Railway detected Java/Gradle project
- [ ] Build completed successfully (check logs)

### Database Setup
- [ ] Added PostgreSQL database to Railway project
- [ ] Database provisioned successfully
- [ ] Database is linked to backend service

### Environment Variables
- [ ] Set `SPRING_PROFILES_ACTIVE=prod`
- [ ] Set `FRONTEND_URL=https://your-app.vercel.app` (update after frontend deployed)
- [ ] Verified `DATABASE_URL` is auto-generated
- [ ] Verified `PORT` is set (or using default 8080)

### Backend Verification
- [ ] Deployment completed successfully
- [ ] Generated public domain for backend
- [ ] Visited `https://your-backend.railway.app/api/departments`
- [ ] API returns JSON with department data
- [ ] No 500 errors in Railway logs

**Backend URL**: ___________________________________

## Frontend Deployment (Vercel)

### Vercel Account Setup
- [ ] Created account at https://vercel.com
- [ ] Signed in with GitHub
- [ ] Email verified (if required)

### Frontend Configuration
- [ ] Updated `frontend/.env.production` with Railway backend URL
- [ ] Committed and pushed changes to GitHub
- [ ] Verified `.env.production` syntax is correct

### Frontend Deployment Steps
- [ ] Created new Vercel project
- [ ] Imported GitHub repository
- [ ] Set Root Directory to `frontend`
- [ ] Framework detected as Create React App
- [ ] Build command: `npm run build`
- [ ] Output directory: `build`

### Environment Variables
- [ ] Added `REACT_APP_API_URL` in Vercel settings
- [ ] Value: `https://your-backend.railway.app/api`
- [ ] Applied to Production, Preview, and Development

### Frontend Verification
- [ ] Deployment completed successfully
- [ ] Visited Vercel URL
- [ ] Frontend loads without errors
- [ ] No console errors in browser
- [ ] Can navigate between pages

**Frontend URL**: ___________________________________

## Integration Testing

### CORS Configuration
- [ ] Updated `FRONTEND_URL` in Railway with exact Vercel URL
- [ ] Railway redeployed automatically
- [ ] No CORS errors in browser console

### Functionality Testing
- [ ] Can view list of departments
- [ ] Can view list of professors
- [ ] Can create new department
- [ ] Can create new professor
- [ ] Can update department
- [ ] Can update professor
- [ ] Can delete department
- [ ] Can delete professor
- [ ] Can view department with all professors
- [ ] Form validation works correctly
- [ ] Error messages display properly

### Database Testing
- [ ] Created test records persist after page refresh
- [ ] Backend restart doesn't delete data (check Railway logs)
- [ ] Can verify data in Railway PostgreSQL database

## Documentation

### Required Documentation
- [ ] README.md contains project overview
- [ ] DEPLOYMENT.md is complete and accurate
- [ ] DEPLOYMENT_QUICKSTART.md is available
- [ ] All URLs are documented

### Submission Materials
- [ ] Documented frontend URL
- [ ] Documented backend API URL
- [ ] Documented GitHub repository URL
- [ ] Screenshots prepared (see below)

## Screenshots for Submission

Take and save the following screenshots:

### 1. Working Application
- [ ] Homepage screenshot
- [ ] Department list screenshot
- [ ] Professor list screenshot
- [ ] Create department form screenshot
- [ ] Department with professors view screenshot

### 2. API Responses
- [ ] Browser showing `/api/departments` JSON response
- [ ] Browser showing `/api/professors` JSON response
- [ ] Browser showing `/api/departments/1/professors` response

### 3. Deployment Dashboards
- [ ] Railway project dashboard showing successful deployment
- [ ] Railway database showing connection
- [ ] Vercel project dashboard showing successful deployment
- [ ] Vercel analytics/overview page

### 4. Developer Console
- [ ] Browser console showing no errors
- [ ] Network tab showing successful API calls

## Final Verification

### Performance Check
- [ ] Frontend loads in < 3 seconds
- [ ] API responses return in < 1 second
- [ ] No browser console warnings or errors
- [ ] Mobile responsive (test on phone or browser dev tools)

### Security Check
- [ ] No exposed credentials in GitHub repository
- [ ] `.env` files are in `.gitignore`
- [ ] CORS only allows your frontend domain
- [ ] HTTPS is working (Railway and Vercel provide this automatically)

### Code Quality
- [ ] No commented-out code blocks
- [ ] Code is properly formatted
- [ ] No console.log statements in production
- [ ] All files are properly organized

## Submission Package

### What to Submit to Your Teacher

1. **URLs Document** (create a simple text file):
   ```
   University Department System - Deployment URLs

   Live Application: https://_____.vercel.app
   API Endpoint: https://_____.railway.app/api
   GitHub Repository: https://github.com/____/UniversityDepartmentSystem

   Test Credentials (if applicable): N/A (no authentication required)

   Notes:
   - Application is fully functional
   - All CRUD operations working
   - Data persists in PostgreSQL database
   ```

2. **Screenshots Folder** containing:
   - All screenshots listed above
   - Name them descriptively (e.g., `01-homepage.png`, `02-department-list.png`)

3. **Optional: Video Demo** (if required):
   - Screen recording showing all CRUD operations
   - Demonstrate creating, reading, updating, deleting
   - Show both departments and professors
   - Max 3-5 minutes

### Submission Checklist
- [ ] URLs document prepared
- [ ] Screenshots organized in folder
- [ ] Video demo recorded (if required)
- [ ] GitHub repository is accessible
- [ ] All links have been tested one final time
- [ ] Submitted through required platform (email, LMS, etc.)

## Post-Deployment Maintenance

### Monitoring (Optional but Recommended)
- [ ] Check Railway usage to ensure staying within free tier
- [ ] Monitor Vercel analytics for traffic
- [ ] Review logs if any issues arise

### Updates
- [ ] Know how to update: `git push` triggers auto-deployment
- [ ] Understand Railway logs for debugging
- [ ] Understand Vercel logs for frontend issues

## Common Issues Reference

| Issue | Solution | Location |
|-------|----------|----------|
| CORS error | Check FRONTEND_URL matches exactly | Railway env vars |
| API not found | Check REACT_APP_API_URL | Vercel env vars |
| Build fails | Check Railway logs | Railway dashboard |
| Database connection error | Verify DATABASE_URL | Railway env vars |
| 404 on page refresh | Ensure vercel.json exists | frontend/vercel.json |
| Data not persisting | Check using PostgreSQL not H2 | Railway database |

## Emergency Contacts

- **Railway Support**: https://railway.app/help
- **Vercel Support**: https://vercel.com/support
- **Your Teacher**: [Add contact info]
- **Teaching Assistant**: [Add contact info]

---

## Sign Off

Once ALL items above are checked:

**Student Name**: _______________________________

**Date Deployed**: _______________________________

**Date Verified**: _______________________________

**Ready for Submission**: ☐ YES

---

## Tips for Success

1. **Don't rush**: Take your time with each step
2. **Test thoroughly**: Don't assume it works, verify it
3. **Document URLs**: Write them down immediately after deployment
4. **Take screenshots early**: Easier to retake now than later
5. **Keep credentials safe**: Save Railway/Vercel login info
6. **Ask for help early**: If stuck for > 30 minutes, seek assistance

---

**Good luck with your submission!**

For detailed help, see:
- [DEPLOYMENT.md](DEPLOYMENT.md) - Complete deployment guide
- [DEPLOYMENT_QUICKSTART.md](DEPLOYMENT_QUICKSTART.md) - Quick reference
- [DEPLOYMENT_ARCHITECTURE.md](DEPLOYMENT_ARCHITECTURE.md) - System architecture

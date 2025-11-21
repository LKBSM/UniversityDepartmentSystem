# Deployment Architecture

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         User's Browser                          │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ HTTPS
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Vercel (Frontend)                            │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  React Application (Static Files)                         │  │
│  │  - Department List/Detail/Form                            │  │
│  │  - Professor List/Detail/Form                             │  │
│  │  - Home Page                                              │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                  │
│  Environment Variables:                                         │
│  REACT_APP_API_URL = https://backend.railway.app/api           │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ REST API Calls
                             │ (GET, POST, PUT, DELETE)
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Railway (Backend)                            │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  Spring Boot Application (Java 17)                        │  │
│  │  ┌─────────────────────────────────────────────────────┐  │  │
│  │  │  Presentation Layer (Controllers)                   │  │  │
│  │  │  - DepartmentController                             │  │  │
│  │  │  - ProfessorController                              │  │  │
│  │  └─────────────────────────────────────────────────────┘  │  │
│  │  ┌─────────────────────────────────────────────────────┐  │  │
│  │  │  Business Logic Layer (Services)                    │  │  │
│  │  │  - DepartmentService                                │  │  │
│  │  │  - ProfessorService                                 │  │  │
│  │  └─────────────────────────────────────────────────────┘  │  │
│  │  ┌─────────────────────────────────────────────────────┐  │  │
│  │  │  Data Access Layer (Repositories)                   │  │  │
│  │  │  - DepartmentRepository                             │  │  │
│  │  │  - ProfessorRepository                              │  │  │
│  │  └─────────────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                  │
│  Environment Variables:                                         │
│  SPRING_PROFILES_ACTIVE = prod                                 │
│  DATABASE_URL = postgresql://...                               │
│  FRONTEND_URL = https://app.vercel.app                         │
│  PORT = 8080                                                   │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ JDBC/JPA
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│              Railway PostgreSQL Database                        │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  Tables:                                                  │  │
│  │  ┌──────────────────┐         ┌────────────────────────┐ │  │
│  │  │   DEPARTMENT     │         │      PROFESSOR         │ │  │
│  │  ├──────────────────┤         ├────────────────────────┤ │  │
│  │  │ id (PK)          │◄────┐   │ id (PK)                │ │  │
│  │  │ name             │     │   │ first_name             │ │  │
│  │  │ code             │     └───│ department_id (FK)     │ │  │
│  │  │ year_established │         │ last_name              │ │  │
│  │  └──────────────────┘         │ email                  │ │  │
│  │                               │ title                  │ │  │
│  │                               └────────────────────────┘ │  │
│  │                                                          │  │
│  │  Seed Data: 10 Departments, 12 Professors              │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                  │
│  Specs: 500MB storage, Automatic backups                       │
└─────────────────────────────────────────────────────────────────┘
```

## Request Flow

### Example: Creating a New Professor

```
1. User fills form in browser
   ↓
2. React sends POST request to:
   https://backend.railway.app/api/professors
   {
     "firstName": "John",
     "lastName": "Doe",
     "email": "john.doe@uni.ca",
     "title": "Professor",
     "departmentId": 1
   }
   ↓
3. Railway backend receives request
   ↓
4. ProfessorController validates CORS (checks FRONTEND_URL)
   ↓
5. Controller validates request data (Bean Validation)
   ↓
6. ProfessorService processes business logic
   ↓
7. ProfessorRepository saves to PostgreSQL database
   ↓
8. Response flows back through layers
   ↓
9. Controller returns 201 Created with professor data
   ↓
10. React receives response and updates UI
```

## Development vs Production

### Development Environment

```
┌─────────────┐         ┌──────────────┐         ┌──────────┐
│   Browser   │────────▶│   localhost  │────────▶│    H2    │
│ localhost:  │         │     :8080    │         │ In-Memory│
│    3000     │◀────────│   (Spring    │◀────────│ Database │
└─────────────┘         │     Boot)    │         └──────────┘
                        └──────────────┘

- No CORS issues (proxy configured)
- H2 console available
- Fast development cycle
- Data resets on restart
```

### Production Environment

```
┌─────────────┐         ┌──────────────┐         ┌───────────┐
│   Browser   │────────▶│   Vercel     │────────▶│  Railway  │
│   (User)    │         │   (React)    │         │ (Spring)  │
│             │◀────────│              │◀────────│           │
└─────────────┘         └──────────────┘         └─────┬─────┘
                                                       │
                                                       ▼
                                                 ┌──────────┐
                                                 │PostgreSQL│
                                                 │ Database │
                                                 └──────────┘

- CORS configured via environment variables
- Persistent PostgreSQL database
- Automatic deployments from GitHub
- SSL/HTTPS encryption
```

## Environment Configuration

### Local Development
```properties
# application.properties
spring.datasource.url=jdbc:h2:mem:universitydb
spring.h2.console.enabled=true
```

### Production
```properties
# application-prod.properties
spring.datasource.url=${DATABASE_URL}
spring.h2.console.enabled=false
```

Activated with: `SPRING_PROFILES_ACTIVE=prod`

## Security Layers

```
┌────────────────────────────────────────┐
│  1. HTTPS Encryption                   │
│     (Automatic with Vercel/Railway)    │
└────────────────────────────────────────┘
             ▼
┌────────────────────────────────────────┐
│  2. CORS Protection                    │
│     (Only allows configured frontend)  │
└────────────────────────────────────────┘
             ▼
┌────────────────────────────────────────┐
│  3. Input Validation                   │
│     (Bean Validation annotations)      │
└────────────────────────────────────────┘
             ▼
┌────────────────────────────────────────┐
│  4. Database Connection Security       │
│     (SSL connection to PostgreSQL)     │
└────────────────────────────────────────┘
```

## Scaling Considerations

### Current Architecture (Free Tier)
- **Frontend**: CDN-distributed, handles high traffic automatically
- **Backend**: Single instance, suitable for ~100 concurrent users
- **Database**: 500MB storage, ~100 connections

### If You Need to Scale
```
Vercel Frontend (Already scaled globally)
        ▼
Load Balancer (Railway Pro)
        ▼
    ┌───┴───┐
    ▼       ▼
Backend  Backend  (Multiple instances)
Instance Instance
    │       │
    └───┬───┘
        ▼
  PostgreSQL
  (Connection pooling)
```

## Monitoring & Logs

### Railway Dashboard
```
Metrics:
- CPU usage
- Memory usage
- Request count
- Response times

Logs:
- Application logs
- Build logs
- Database queries (if enabled)
```

### Vercel Dashboard
```
Analytics:
- Page views
- Unique visitors
- Performance metrics
- Build history

Logs:
- Build logs
- Function logs
- Edge network logs
```

## Deployment Pipeline

```
┌──────────────────┐
│  Local Changes   │
│  (Your Computer) │
└────────┬─────────┘
         │
         │ git push
         ▼
┌──────────────────┐
│     GitHub       │
│   (Repository)   │
└────┬────────┬────┘
     │        │
     │        │ (Webhooks)
     │        │
     ▼        ▼
┌─────────┐  ┌─────────┐
│ Railway │  │ Vercel  │
│  Build  │  │  Build  │
└────┬────┘  └────┬────┘
     │            │
     │ Deploy     │ Deploy
     ▼            ▼
┌─────────┐  ┌─────────┐
│Backend  │  │Frontend │
│ Live!   │  │ Live!   │
└─────────┘  └─────────┘
```

**Total deployment time**: ~2-3 minutes after git push

## Cost Breakdown

### Free Tier Limits

| Service | Free Tier | Estimated Project Usage | Sufficient? |
|---------|-----------|-------------------------|-------------|
| Railway | $5 credit (~500 hours) | ~730 hours/month | ⚠️ If always-on |
| Railway | $5 credit (~500 hours) | ~50 hours/month | ✅ If on-demand |
| Vercel | 100GB bandwidth | ~1-5GB/month | ✅ Yes |
| PostgreSQL | 500MB | ~10-50MB | ✅ Yes |

**Note**: Railway backend may sleep after inactivity (15 min). This is normal for free tier.

## Best Practices

1. **Environment Variables**: Never commit `.env` files
2. **Database**: Use migrations for schema changes
3. **CORS**: Only allow your frontend URL
4. **Logs**: Monitor Railway logs for errors
5. **Backups**: Railway provides automatic database backups
6. **Updates**: Push to GitHub for automatic deployment

---

For detailed deployment instructions, see:
- [DEPLOYMENT.md](DEPLOYMENT.md) - Complete guide
- [DEPLOYMENT_QUICKSTART.md](DEPLOYMENT_QUICKSTART.md) - Quick start

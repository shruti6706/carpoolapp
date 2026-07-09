# 🚗 CarpoolApp — Smart Carpool & Safe Ride Sharing

A React Native mobile app built with Expo for the Smart Carpool & Safe Ride Sharing Platform.

## Tech Stack

- React Native (Expo SDK 54)
- TypeScript
- Expo Router (file-based routing)
- Axios (API calls)
- AsyncStorage (JWT token storage)

## Prerequisites

Before running this project, make sure you have:

- [Node.js](https://nodejs.org/) v18 or higher
- [Expo Go](https://expo.dev/go) app installed on your Android phone
- Backend running (see backend repo instructions)

## Backend Setup

This app requires the Spring Boot backend to be running.

Clone and run the backend first:
```bash
git clone https://github.com/shruti6706/carpoolapp.git
cd carpoolapp
# switch to main branch for backend
git checkout main
```

**Option A — Run with Docker (recommended):**
```bash
docker-compose up --build
```

**Option B — Run with IntelliJ:**
- Open project in IntelliJ IDEA
- Make sure PostgreSQL is running
- Run `BackendApplication.java`

Backend runs on port `8080`.

## Frontend Setup

**Step 1 — Clone the repo:**
```bash
git clone https://github.com/shruti6706/carpoolapp.git
cd carpoolapp
git checkout frontend
```

**Step 2 — Install dependencies:**
```bash
npm install
```

**Step 3 — Configure environment:**

Copy the example env file:
```bash
cp .env.example .env
```

Open `.env` and set your backend URL:


If running backend with Docker:
EXPO_PUBLIC_API_URL=http://localhost:8080
If running backend with IntelliJ (local):
Find your PC IP → run ipconfig (Windows) or ifconfig (Mac/Linux)
Look for WiFi IPv4 Address
EXPO_PUBLIC_API_URL=http://YOUR_WIFI_IP:8080

**Step 4 — Start the app:**
```bash
npx expo start
```

**Step 5 — Open on phone:**
- Make sure your phone and PC are on the **same WiFi network**
- Open **Expo Go** on your Android phone
- Scan the QR code shown in terminal

## Common Issues

**Registration/Login fails:**
- Make sure backend is running on port 8080
- Check your IP in `.env` is correct
- Make sure phone and PC are on same WiFi

**QR code not working:**
- Make sure Expo Go is updated to latest version
- Try restarting with `npx expo start --clear`

**IP keeps changing:**
- Update `EXPO_PUBLIC_API_URL` in `.env` with new IP
- Run `ipconfig` (Windows) to find current IP

## Features

- ✅ User Authentication (Register/Login/JWT)
- ✅ User Profile (View/Edit)
- 🚧 Ride Management (Create/Search)
- 🚧 Booking System (Book/Accept/Reject/Cancel)
- 🚧 Ratings & Reviews
- 🚧 Real-time Tracking (WebSocket)
- 🚧 Push Notifications (FCM)
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/logout": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/employee": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/staffing-manager": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
})

import React from 'react';
import ReactDOM from 'react-dom/client';
import './assets/style/index.css';
import App from './App';
import {RouterProvider, createBrowserRouter} from 'react-router-dom'


const router = createBrowserRouter([{
  path:'/*',
  element:<App/>
}])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>
);
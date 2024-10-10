import './App.css';
import { GetTicketForm } from './components/GetTicketFormComponent';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Routes, Route } from 'react-router-dom';

function App() {
  return (
   <Routes>
      <Route index element={
          <GetTicketForm/>
      } />
   </Routes>
  );
}

export default App;

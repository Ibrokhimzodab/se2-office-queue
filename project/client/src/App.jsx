import './App.css';
import { GetTicketForm } from './components/GetTicketFormComponent';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Routes, Route } from 'react-router-dom';
import { DisplayTicket} from './components/DisplayTicket.jsx'

function App() {
  return (
   <Routes>
      <Route path='/' element={
          <GetTicketForm/>
      } />
      <Route path='/ticketsummary' element={
          <DisplayTicket/>
      } />
   </Routes>
  );
}

export default App;

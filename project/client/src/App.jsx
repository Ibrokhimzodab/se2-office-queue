// import './App.css';
import { GetTicketForm } from './components/GetTicketFormComponent';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Routes, Route } from 'react-router-dom';
import { DisplayTicket} from './components/DisplayTicket.jsx'
import { QueueList } from './components/QueueList.jsx';

function App() {
  return (
   <Routes>
      <Route path='/' element={
          <GetTicketForm/>
      } />
      <Route path='/ticket/:service' element={
          <DisplayTicket/>
      } />
      <Route path='/queue' element={
          <QueueList/>
      } />
   </Routes>
  );
}

export default App;

import { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import '../assets/style/DisplayTicket.css';  // Importa il file CSS
import API from '../API.mjs'
import { Ticket } from "../obj/Ticket.mjs";

export function DisplayTicket(props) {
  const navigate = useNavigate();
  const [counter, setCounter] = useState(30);
  const [ticketID, setTicketID] = useState('');
  const [wait,setWait] = useState('')
  const { service, id } = useParams();
  const [loading, setLoading] = useState(true)


  useEffect(() => {
    // Simulating service load
    const loadInfoTicket = async () => {
        try {
            API.getTicket(id).then((tick) =>{
              setTicketID(tick.waitListCode);
              setWait(tick.estimatedTime)
            })
        } catch (error) {
            console.error("Error getting ticket:", error);
        } finally {
            setLoading(false);
        }
    };
    loadInfoTicket();
}, []);

  useEffect(() => {
    const timer = counter > 0 && setInterval(() => setCounter(counter - 1), 1000);
    
    if (counter === 0) {
      setCounter(-1);  // Evita loop di navigazione
      navigate('/');
    }

    return () => clearInterval(timer);
  }, [counter, navigate]);

  return (
    <div className="center-card">
      {!loading && <Card className="card" style={{ width: '18rem' }}>
        <div className="card-number">{ticketID}</div>
        <Card.Body>
          <Card.Title className="card-title">{service}</Card.Title>
          <Card.Text className="card-text">
            Estimated Waiting Time: {wait}
          </Card.Text>
        </Card.Body>
      </Card>}
      <div className="timer">Timer: {counter} seconds</div>
    </div>
  );
}



import { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import '../assets/style/DisplayTicket.css';  // Importa il file CSS
import API from '../API.mjs'

export function DisplayTicket(props) {
  const navigate = useNavigate();
  const [counter, setCounter] = useState(30);
  const [ticket, setTicket] = useState();
  const { service } = useParams();
  const [loading, setLoading] = useState(true)


  useEffect(() => {
    // Simulating service load
    const loadInfoTicket = async () => {
        try {
            const ticket = API.getTicket(service);
            setTicket(ticket);
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
        <div className="card-number">{props.number}</div>
        <Card.Body>
          <Card.Title className="card-title">{service}</Card.Title>
          <Card.Text className="card-text">
            Estimated Waiting Time:
          </Card.Text>
        </Card.Body>
      </Card>}
      <div className="timer">Timer: {counter} seconds</div>
    </div>
  );
}



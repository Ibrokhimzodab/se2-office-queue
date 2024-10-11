import { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import '../assets/style/DisplayTicket.css';  // Importa il file CSS

export function DisplayTicket(props) {
  const navigate = useNavigate();
  const [counter, setCounter] = useState(30);
  const param = useParams();

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
      <Card className="card" style={{ width: '18rem' }}>
        <div className="card-number">{props.number || 123}</div>
        <Card.Body>
          <Card.Title className="card-title">{param.service}</Card.Title>
          <Card.Text className="card-text">
            Waiting Time:
          </Card.Text>
        </Card.Body>
      </Card>
      <div className="timer">Timer: {counter}</div>
    </div>
  );
}



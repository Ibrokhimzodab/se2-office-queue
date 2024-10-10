import { useEffect, useState, useContext } from "react";
import { Container,Col, Button,Navbar,Row,Card, Alert } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";



export function DisplayTicket(props){

    const navigate = useNavigate();
    const [counter,setCounter] = useState(30)



    useEffect(() =>{
        const timer =
        counter > 0 && setInterval(() => setCounter(counter - 1), 1000);
        if(counter==0){
            setCounter(-1);
            navigate('/')
        }
            
        return () => clearInterval(timer);

    },[counter])





    return(
        <>
            <Card style={{ width: '18rem' }}>
                <Card.Img variant="top" src="holder.js/100px180?text=number" />
                <Card.Body>
                    <Card.Title>Card Title</Card.Title>
                    <Card.Text>
                        Waiting Time:
                    </Card.Text>
                </Card.Body>
            </Card>
            <div style={{fontSize: 'large'}}>Timer: {counter}</div>
        </>
    )
}


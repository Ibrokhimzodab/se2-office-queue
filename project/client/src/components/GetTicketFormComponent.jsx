import React, { useEffect, useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import '../assets/style/GetTicketForm.css';

const INITIAL_SERVICE = [
  1,3,4,5
];

export function GetTicketForm(props) {
    const navigate = useNavigate();
    const [service, setService] = useState();
    const [serviceList, setServiceList] = useState([]);
    const [loading, setLoading] = useState(true);
    let List = [];
    const handleTicketSelection = () => {
      navigate(`/ticket/${service}`);

    };
    /*
    useEffect(()=>{
      loadService().then((my_service)=>{
          setServiceList(my_service);
          setLoading(false);
      })
      
  }, [])

  */

    if(loading===true){
      console.log(INITIAL_SERVICE)
    INITIAL_SERVICE.forEach((s)=>List.push(s))
      setServiceList(List);
      setLoading(false);
    }
      

  
    return (
      <div className="home-page">
        <Container className="text-center">
          <h1 className="my-5">Select Service</h1>
          {!loading && <Form.Select aria-label="services-list" onChange={ev => setService(ev.target.value)} >
            { serviceList.map((s) => <option value={s}>{s}</option>)}
          </Form.Select>
          }
          <Button variant="primary" size="lg" className="mx-3" onClick={handleTicketSelection}>
            Get Ticket
          </Button>

        </Container>
      </div>
    );
  }
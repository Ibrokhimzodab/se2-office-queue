import React, { useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import './GetTicketFormCSS.css';

export function GetTicketForm(props) {
    const navigate = useNavigate();
    const [service, setService] = useState("Service 1");

    const handleTicketSelection = () => {
      navigate(`/ticket/${service}`);

    };
  
    return (
      <div className="home-page">
        <Container className="text-center">
          <h1 className="my-5">Select Service</h1>
          <Form.Select aria-label="services-list" onChange={ev => setService(ev.target.value)} >
            {/*<option></option>*/}
            <option value="Service 1">Service 1</option>
            <option value="Service 2">Service 2</option>
            <option value="Service 3">Service 3</option>
            <option value="Service 4">Service 4</option>
          </Form.Select>
          <Button variant="primary" size="lg" className="mx-3" onClick={handleTicketSelection}>
            Get Ticket
          </Button>

        </Container>
      </div>
    );
  }
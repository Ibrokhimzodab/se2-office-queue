import React, { useEffect, useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import '../assets/style/GetTicketForm.css';
import API from '../API.mjs'
import {Service} from '../obj/Service.mjs'

const MOCK_SERVICE_LIST = [
  1,3,4,5
];

export function GetTicketForm(props) {
    const navigate = useNavigate();
    const [selectedService, setSelectedService] = useState(null);
    const [selectID,setSelectID] = useState(null)
    const [serviceList, setServiceList] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Simulating service load
        const loadServiceList = async () => {
            try {
                API.loadService().then((list) =>{
                  let serv = []
                  for(const l of list){
                    serv.push(new Service(l.id,l.name))
                  }
                  setServiceList(serv);
                })
            } catch (error) {
                console.error("Error loading service list:", error);
            } finally {
                setLoading(false);
            }
        };
        loadServiceList();
    }, []);

    const handleServiceSelection = (e) => {
        for(const l of serviceList){
          if(l.name == e.target.value){
            console.log('ok')
            setSelectID(l.id)
          }
        }
        setSelectedService(e.target.value);
    };

    const handleGetTicketClick = () => {
        // Navigate only if a service is selected
        if (selectedService) {
            navigate(`/ticket/${selectedService}/${selectID}`);
        } else {
            alert("Please select a service.");
        }
    };

  
    return (
      <div className="home-page">
        <Container className="text-center">
          <h1 className="my-5">Select Service</h1>
          {!loading && (
            <Form.Select 
              aria-label="services-list" 
              value={selectedService || ''}
              onChange={handleServiceSelection} >
                <option value="" disabled>Select a service</option>
                { serviceList.map((service) => 
                  <option key={service.id} value={service.name}>{service.name}</option>
                )}
            </Form.Select>
          )}
          <Button 
            variant="primary" 
            size="lg" 
            className="mx-3"
            disabled={loading || !selectedService}
            onClick={handleGetTicketClick}>
              Get Ticket
          </Button>
        </Container>
      </div>
    );
  }
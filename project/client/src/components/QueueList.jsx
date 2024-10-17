import React, { useState, useEffect } from 'react';
import { Table, Button, Container, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import API from '../API.mjs';
import '../assets/style/QueueListCSS.css';
import { Ticket } from '../obj/Ticket.mjs';
// Mocked API function for demonstration purposes
const fetchQueueList = async () => {
  // Simulate a real API call
  return [
    { id: 65, serviceType: 'Service Type Name', counter: 1 },
    { id: 66, serviceType: 'Another Service', counter: 2 },
    { id: 67, serviceType: 'More Services', counter: 3 }
  ];
};
export function QueueList(props) {
    const navigate = useNavigate();
    const [queueList, setQueueList] = useState([]);  
    const [loading, setLoading] = useState(true);    
    const [error, setError] = useState(null); 
    const [counter, setCounter] = useState(1);
    const [selectedCounter,setSelectedCounter] = useState();

    const loadQueueList = async () => {
      try {
        setLoading(true);
        const list = await API.getQueue().then((my_q) =>{
          let queue = []
          console.log(my_q)
          for(const q of my_q){
            for(const t of q.tickets)
            {
              queue.push(new Ticket(t.waitListCode,0,q.serviceName,0))
            }
            
          }
          
          setQueueList(queue);
        })
      } catch (err) {
        setError('Failed to fetch queue list');
      } finally {
        setLoading(false);
      }
    };

    const loadNewQueueList = async () => {
      try {
        setLoading(true);
        await API.callNext(counter)
        const list = await API.getQueue().then((my_q) =>{
          let queue = []
          for(const q of my_q){
            queue.push(new Ticket(q.tickets[0].waitListCode,0,q.serviceName,0))
          }
          
          setQueueList(queue);
        })
      } catch (err) {
        setError('Failed to fetch queue list');
      } finally {
        setLoading(false);
      }
    };

    useEffect(() => {
      loadQueueList();
    }, []);

    const handleNextCustomer = async () => {
      console.log(counter)
      await loadNewQueueList();
    };

    const handleCounterSelection = (e) => {
      setCounter(e.target.value);
  };

    if (error) {
      return <div className="error-message">{error}</div>;
    }
    return (
      <Container className='container'>
      <Table className='queue' hover>
        <thead>
            <tr>
            <th>N°</th>
            <th>Service Type</th>
            <th className='counter'>Counter</th>
            </tr>
        </thead>
        <tbody>
          {queueList.length > 0 ? (
            queueList.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.service}</td>
                <td className="counter">{item.counter}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3">No customers in the queue.</td>
            </tr>
          )}
        </tbody>
    </Table>
    <Form.Select 
              aria-label="counter-list" 
            //   value={selectedCounter || ''}
              onChange={handleCounterSelection} >
                <option value="" disabled>Select a counter</option>
                  <option value='1'>1</option>
                  <option value='2'>2</option>
                  <option value='3'>3</option>
                  <option value='4'>4</option>
            </Form.Select>
    <Button className='button_call' onClick={handleNextCustomer}>Call next</Button>
    </Container>
    );
  }


  
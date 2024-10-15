import React, { useState, useEffect } from 'react';
import { Table, Button, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import '../assets/style/QueueListCSS.css';
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

    const loadQueueList = async () => {
      try {
        setLoading(true);
        const list = await fetchQueueList();  // Replace with actual API call
        setQueueList(list);
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
      await loadQueueList();
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
                <td>{item.serviceType}</td>
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
    <Button className='button_call' onClick={handleNextCustomer}>Call next</Button>
    </Container>
    );
  }


  
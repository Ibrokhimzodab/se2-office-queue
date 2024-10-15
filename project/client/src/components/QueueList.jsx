import React, { useState } from 'react';
import { Table, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import './QueueListCSS.css';

export function QueueList(props) {
    const navigate = useNavigate();

    return (
      <>
      <Table className='queue' hover>
        <thead>
            <tr>
            <th>N°</th>
            <th>Service Type</th>
            <th className='counter'>Counter</th>
            </tr>
        </thead>
        <tbody>
            <tr>
            <td>65</td>
            <td>Service Type Name</td>
            <td className='counter'>1</td>
            </tr>
            <tr>
            <td>65</td>
            <td>Service Type Name</td>
            <td className='counter'>1</td>
            </tr>
            <tr>
            <td>65</td>
            <td>Service Type Name</td>
            <td className='counter'>1</td>
            </tr>
        </tbody>
    </Table>
    <Button className='button_call'>Call next</Button>
    </>
    );
  }
const url = "http://localhost:8001"

async function loadService() {
    const response = await fetch(`${url}/services`)
    const services = await response.json()
    return services;
}

async function getTicket(service) {
    const response = await fetch(`${url}/ticket?serviceId=${service}`)
        if(response.ok){
            const ticket = await response.json();
            return ticket;
        }
        else {
            const errDetail = await response.json();
            if (errDetail.error)
                throw errDetail.error
            if (errDetail.message)
                throw errDetail.message
    
            throw "Something went wrong"
        }
        
}

async function callNext(counterID) {
    try {
        const response = await fetch(`${url}/counters/${counterID}/next`,
            {
                method: "POST",
                headers:{
                    'Content-Type':'application/json'
                }
            })
        if (response.ok) {
            return;
        } else {
            const errDetail = await response.json();
            if (errDetail.error)
                throw errDetail.error;
            if (errDetail.message)
                throw errDetail.message;
            
            throw "Something went wrong while calling the next customer.";
        }
    } catch (error) {
        console.error(`Error fetching queue list for counter ${counterID}:`, error);
        throw error;  
    }
}

async function getQueue(params) {
    const response = await fetch(`${url}/queues`)
    const queue = await response.json()
    return queue;
}

const API ={loadService, getTicket, callNext, getQueue}

export default API;
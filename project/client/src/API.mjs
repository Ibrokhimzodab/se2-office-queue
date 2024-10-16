const url = "http://localhost:8001"

async function loadService() {
    const response = await fetch(`${url}/services`)
    const services = await response.json()
    let s_name ;
    console.log(s_name)
    services.forEach((s) => s_name.push(s.name))
    return s_name;
}

async function getTicket(service) {
    const response = await fetch(`${url}/ticket?service=${service}` )
    
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

async function fetchQueueList(counterID) {
    try {
        const response = await fetch(`${url}/counters/${counterID}/next`);
        
        if (response.ok) {
            const queueList = await response.json();
            return queueList;
        } else {
            const errDetail = await response.json();
            if (errDetail.error)
                throw errDetail.error;
            if (errDetail.message)
                throw errDetail.message;
            
            throw "Something went wrong while fetching the queue list.";
        }
    } catch (error) {
        console.error(`Error fetching queue list for counter ${counterID}:`, error);
        throw error;  
    }
}

const API ={loadService, getTicket, fetchQueueList}

export default API;
const url = "http://localhost:8001"

async function loadService(pathname) {
    const response = await fetch(`${url}${pathname}`)
    const services = await response.json()
    return services
}

async function getTicket(service) {
    const response = await fetch(`${url}/add`,
        {
            method: "POST",
            headers:{
                'Content-Type':'application/json'
            },
            body: JSON.stringify(service)
        })
    
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


const API ={loadService}

export default API
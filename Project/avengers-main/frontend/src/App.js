import './App.css';
import { React } from "react";
import { Routes, Route, useNavigate, BrowserRouter } from 'react-router-dom';
import { RabbitMQ } from './components/rabbitmq';
import Customer from './components/customer';
import Restaurant from './components/restaurant';
import DeliveryAgent from './components/deliveryagent';

function Root() {
  const navigate = useNavigate();

  const navigateCustomer = () => {
    navigate('/customer');
  };

  const navigateRestaurant = () => {
    navigate('/restaurant');
  };

  const navigateDeliveryAgent = () => {
    navigate('/deliveryagent')
  }


  return (
    <div className="App">
      <RabbitMQ />
      <div>
        <button onClick={navigateCustomer}>Customer</button>
        <button onClick={navigateRestaurant}>Restaurant</button>
        <button onClick={navigateDeliveryAgent}>Delivery Agent</button>

        {/* Defines routes for navigation between pages */}
        <Routes>
          <Route path="/customer" exact element={<Customer />} />
          <Route path="/restaurant" exact element={<Restaurant />} />
          <Route path="/deliveryagent" exact element={<DeliveryAgent/>} />
        </Routes>
      </div>

    </div>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Root />
    </BrowserRouter>
  );
}

export default App;

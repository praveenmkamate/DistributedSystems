import { Component } from "react";
import Stomp from 'stompjs';

export class RabbitMQ extends Component {

    connectRabbit() {
        let stompClient;
        var ws = new WebSocket('ws://localhost:15674/ws');

        const headers = {
            'login': 'guest',
            'passcode': 'guest',
            'durable': true,
            'auto-delete': false
        };
        stompClient = Stomp.over(ws);

        stompClient.connect(headers, function (frame) {
            console.log('Connected')
            stompClient.subscribe('/queue/restaurant-1-orders', function (message) {
                console.log(message)
            });
        });
    };

    componentDidMount() {
        // this.connectRabbit();
    }

    render() {
        return;
    }
}
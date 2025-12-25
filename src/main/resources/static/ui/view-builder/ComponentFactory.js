import { Row } from './components/Row.js';
import { Grid } from './components/Grid.js';
import { Button } from './components/Button.js';
import { Input } from './components/Input.js';
import { CardBody } from './components/CardBody.js';
import { Form } from './components/Form.js';
import { Card } from './components/Card.js';
import { Layout } from './components/Layout.js';

export class ComponentFactory {
    static instanceMap() {
        return {
                "button": new Button(),
                "form": new Form(),
                "grid": new Grid(App.grid),
                "row": new Row(),
                "card": new Card(),
                "card-body": new CardBody(),
                "input": new Input(),
                "layout": new Layout()
            };
    }

    static instance(type) {
        switch (type) {
            case 'row':
                return new Row();

            case 'button':
                return new Button();

            case 'input':
                return new Input();

            case 'card-body':
                return new CardBody();

            case 'form':
                return new Form();

            case 'grid':
                return new Grid(App.grid);

            case 'card':
                return new Card();

            case 'layout':
                return new Layout();

            default:
                return null;
        }
    }

    static getComponentIdMap() {
        return {
               "row":0,
               "button":0,
               "form":0,
               "card":0,
               "grid":0,
               "input":0,
               "card-body":0
           }
    }
}
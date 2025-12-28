import { Row } from './components/Row.js';
import { Grid } from './components/Grid.js';
import { Button } from './components/Button.js';
import { Input } from './components/Input.js';
import { CardBody } from './components/CardBody.js';
import { Form } from './components/Form.js';
import { Card } from './components/Card.js';
import { Layout } from './components/Layout.js';
import { CustomHtml } from './components/CustomHtml.js';

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
                "layout": new Layout(),
                "custom-html": new CustomHtml()
            };
    }

    static instance(type) {
        const instanceMap = ComponentFactory.instanceMap();
        return instanceMap[type];
    }

    static getComponentIdMap() {
        let componentIdMap = {};

        const instanceMap = ComponentFactory.instanceMap();
        for(let key in instanceMap) {
            componentIdMap[key] = 0;
        }

        return componentIdMap;
    }
}
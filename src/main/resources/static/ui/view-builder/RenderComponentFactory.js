import { Row } from './components/Row.js';
import { Grid } from './components/Grid.js';
import { Button } from './components/Button.js';
import { Input } from './components/Input.js';
import { CardBody } from './components/CardBody.js';
import { Form } from './components/Form.js';
import { Card } from './components/Card.js';

export class RenderComponentFactory {
    static create(el, renderer) {
        switch (el.type) {
            case 'row':
                return {
                    instance: new Row(),
                    children: renderer.component(el.children)
                };

            case 'button':
                return { instance: new Button(), children: null };

            case 'input':
                return { instance: new Input(), children: null };

            case 'card-body':
                return {
                    instance: new CardBody(),
                    children: renderer.component(el.children)
                };

            case 'form':
                return {
                    instance: new Form(),
                    children: renderer.component(el.children)
                };

            case 'grid':
                return { instance: new Grid(App.grid), children: null };

            case 'card': {
                const header = [];
                const body = [];

                for (const child of el.children ?? []) {
                    if (child.type === 'card-body') body.push(child);
                    else header.push(child);
                }

                return {
                    instance: new Card(),
                    children: {
                        cardHeader: renderer.component(header),
                        cardChildren: renderer.component(body)
                    }
                };
            }

            default:
            return null;
        }
    }
}
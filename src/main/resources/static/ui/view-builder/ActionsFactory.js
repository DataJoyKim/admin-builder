import { Workflow } from './actions/Workflow.js';
import { Script } from './actions/Script.js';

export class ActionsFactory {

    static instance(type) {
        switch (type) {
            case 'WORKFLOW':
                return new Workflow();

            case 'SCRIPT':
                return new Script();

            default:
                return null;
        }
    }
}
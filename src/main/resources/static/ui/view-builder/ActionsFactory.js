import { Workflow } from './actions/Workflow.js';

export class ActionsFactory {

    static instance(type) {
        switch (type) {
            case 'WORKFLOW':
                return new Workflow();

            default:
                return null;
        }
    }
}
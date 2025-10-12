import { ActionButton } from './components/ActionButton.js';
import { Card } from './components/Card.js';
import { Select } from './components/Select.js';
import { TextInput } from './components/TextInput.js';
import { CardHeader } from './components/CardHeader.js';
import { CardBody } from './components/CardBody.js';

import { Col } from './layout/Col.js';
import { Row } from './layout/Row.js';
import { Layout } from './layout/Layout.js';

import { Actions } from './actions/Actions.js';
import { Script } from './actions/Script.js';
import { Workflow } from './actions/Workflow.js';
import { ResultEvent } from './actions/ResultEvent.js';
import { FaultEvent } from './actions/FaultEvent.js';

import { Data } from './data/Data.js';
import { Codes } from './data/Codes.js';
import { Code } from './data/Code.js';
import { Messages } from './data/Messages.js';
import { Message } from './data/Message.js';
import { BindMessage } from './data/BindMessage.js';

customElements.define('mf-action-button', ActionButton);
customElements.define('mf-card', Card);
customElements.define('mf-card-header', CardHeader);
customElements.define('mf-card-body', CardBody);
customElements.define('mf-select', Select);
customElements.define('mf-text-input', TextInput);

customElements.define('mf-actions', Actions);
customElements.define('mf-script', Script);
customElements.define('mf-workflow', Workflow);
customElements.define('mf-result-event', ResultEvent);
customElements.define('mf-fault-event', FaultEvent);

customElements.define('mf-data', Data);
customElements.define('mf-codes', Codes);
customElements.define('mf-code', Code);
customElements.define('mf-messages', Messages);
customElements.define('mf-message', Message);
customElements.define('mf-bind-message', BindMessage);

customElements.define('mf-col', Col);
customElements.define('mf-row', Row);
customElements.define('mf-layout', Layout);

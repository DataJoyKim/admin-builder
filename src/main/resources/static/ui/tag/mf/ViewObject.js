export class ViewObject extends HTMLElement {
    _messageVariableName = '_messages';
    _codeVariableName = '_codes';
    _dataReadyEventName = 'mf-data-ready';
    _actionsReadyEventName = 'mf-actions-ready';
    _actionsTagName = 'mf-actions';
    _dataTagName = 'mf-data';
    _messagesTagName = 'mf-messages';
    _messageTagName = 'mf-message';
    _bindMessageTagName = 'mf-bind-message';
    _resultEventTagName = 'mf-result-event';
    _faultEventTagName = 'mf-fault-event';
    _scriptTagName = 'mf-script';
    _debug = true;

    id;

    /*
    * 생성 이후에 DOM 에 연결되어지면 실행
    */
    connectedCallback() {
        this.id = this.getAttribute('id');

        this.render();
    }

    /*
    * 렌더링
    * 상속 시 @Overwrite 하여 구현
    */
    render() {}

    getId() {
        return this.id;
    }

    ready(eventName, _callback) {
        this.readyCount = 0;
        this.childTagCount = this.querySelectorAll('*').length;
        this.addEventListener(eventName, (event) => {
            _callback(event);

            this.readyCount++;
            this.logging('[ready]' + eventName, this.childTagCount+':'+this.readyCount);
            if (this.readyCount === this.childTagCount) {
                this.remove();
            }
        });
    }

    sendReadyEvent(eventName, message) {
        this.dispatchEvent(new CustomEvent(eventName, { bubbles: true, detail:message}));
    }

    readyComplete(message) {
        const actionsTag = this.closest(this._actionsTagName);
        const dataTag = this.closest(this._dataTagName);

        if(dataTag) {
            this.sendReadyEvent(this.getDataReadyEventName(), message);
        }
        else if(actionsTag) {
            this.sendReadyEvent(this.getActionsReadyEventName(), message);
        }
    }

    getDataReadyEventName() {
        return this._dataReadyEventName;
    }

    getActionsReadyEventName() {
        return this._actionsReadyEventName;
    }

    getMessagesTagName() {
        return this._messagesTagName;
    }

    getMessageTagName() {
        return this._messageTagName;
    }

    getMessageVariable() {
        return window[this._messageVariableName];
    }

    setMessageVariable(value) {
        window[this._messageVariableName] = value;
    }

    getMessageVariableName() {
        return this._messageVariableName;
    }

    getCodeVariable() {
        return window[this._codeVariableName];
    }

    setCodeVariable(value) {
        window[this._codeVariableName] = value;
    }

    getBindMessageTagName() {
        return this._bindMessageTagName;
    }

    getResultEventTagName() {
        return this._resultEventTagName;
    }

    getFaultEventTagName() {
        return this._faultEventTagName;
    }

    getScriptTagName() {
        return this._scriptTagName;
    }

    logging(kind, msg) {
        if(this._debug) {
            console.log(kind, msg);
        }
    }
}
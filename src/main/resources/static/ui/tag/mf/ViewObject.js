export class ViewObject extends HTMLElement {
    _messageVariableName = 'messages';
    _codeVariableName = 'codes';
    _dataReadyEventName = 'mf-data-ready';
    _actionsReadyEventName = 'mf-actions-ready';
    _actionsTagName = 'mf-actions';
    _dataTagName = 'mf-data';
    _layoutTagName = 'mf-layout';
    _messagesTagName = 'mf-messages';
    _messageTagName = 'mf-message';
    _bindMessageTagName = 'mf-bind-message';
    _resultEventTagName = 'mf-result-event';
    _faultEventTagName = 'mf-fault-event';
    _scriptTagName = 'mf-script';
    _codeTagName = 'mf-code';
    _debug = true;

    id;
    /*
    * 생성 이후에 DOM 에 연결되어지면 실행
    */
    connectedCallback() {
        let tagName = this.tagName.toLowerCase();
        console.log('[tag rendering]',tagName);

        this.id = this.getAttribute('id');

        // 컴포넌트 준비완료 이벤트 수신 등록
        this.registerReady(tagName);

        this.render();

        // 컴포넌트 준비완료
        this.readyComplete();
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
            this.readyCount++;
            this.logging('[ready ' + eventName +"]", this.childTagCount+':'+this.readyCount);
            if (this.readyCount === this.childTagCount) {
                _callback(this, event);
            }
        });
    }

    sendReadyEvent(eventName, message) {
        this.dispatchEvent(new CustomEvent(eventName, { bubbles: true, detail:message}));
    }

    registerReady(tagName) {
        if(tagName == this._dataTagName) {
            this.ready(this.getDataReadyEventName(), function(tag, e){
                tag.remove();
            });
        }
        else if(tagName == this._actionsTagName) {
            this.ready(this.getActionsReadyEventName(), function(tag, e){
                tag.remove();
            });
        }
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
        return window.App[this._messageVariableName];
    }

    setMessageVariable(value) {
        window.App = window.App || {};
        window.App[this._messageVariableName] = value;
    }

    getMessageVariableName() {
        return 'App.'+this._messageVariableName;
    }

    getCodeVariable() {
        return window.App[this._codeVariableName];
    }

    setCodeVariable(value) {
        window.App = window.App || {};
        window.App[this._codeVariableName] = value;
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

    getCodeTagName() {
        return this._codeTagName;
    }

    logging(kind, msg) {
        if(this._debug) {
            console.log(kind, msg);
        }
    }
}
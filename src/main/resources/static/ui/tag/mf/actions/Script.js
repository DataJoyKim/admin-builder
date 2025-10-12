import { AbstractActions } from './AbstractActions.js';

export class Script extends AbstractActions {
    render() {
        const fnName = this.getAttribute('name');
        if (!fnName) {
            return;
        }

        const code = this.textContent.trim();
        if (!code) {
            return;
        }

        // Function 생성
        const fn = new Function(code);

        // window 객체에 저장
        window[fnName] = fn;

        // 컴포넌트 준비완료
        super.readyComplete(super.getReadyEventName());
    }
}
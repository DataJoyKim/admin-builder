import { AbstractActions } from './AbstractActions.js';

export class Script extends AbstractActions {
    render() {
        const fnName = this.getAttribute('name');
        if (!fnName) {
            return;
        }

        // 함수 생성
        const code = this.textContent.trim();

        window[fnName] = new Function(code);

        // 컴포넌트 준비완료
        super.readyComplete();
    }
}
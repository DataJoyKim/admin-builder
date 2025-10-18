import { AbstractActions } from './AbstractActions.js';

export class Script extends AbstractActions {
    render() {
        const fnName = this.getAttribute('name');
        if (!fnName) {
            return;
        }

        const argsParams = this.getAttribute('args');

        // 함수 생성
        const code = this.textContent.trim();

        if(argsParams) {
            const args = argsParams.split(",");
            window[fnName] = new Function(...args, code);
        }
        else {
            window[fnName] = new Function(code);
        }

        // 컴포넌트 준비완료
        super.readyComplete();
    }
}
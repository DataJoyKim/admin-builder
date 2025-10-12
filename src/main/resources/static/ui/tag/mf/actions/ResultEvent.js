import { AbstractActions } from './AbstractActions.js';

export class ResultEvent extends AbstractActions {
    render() {
        // 컴포넌트 준비완료
        super.readyComplete();
    }
}
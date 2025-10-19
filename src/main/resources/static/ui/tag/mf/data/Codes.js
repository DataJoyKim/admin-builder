import { AbstractData } from './AbstractData.js';

export class Codes extends AbstractData {
    render() {
        super.setCodeVariable({});

        let self = this;

        let codes = new Array();
        for (const child of this.children) {
            let childTagName = child.tagName.toLowerCase();

            if(childTagName != super.getCodeTagName()) {
                continue;
            }

            codes.push({
                name: child.getAttribute('name'),
                type: child.getAttribute('type')
            });
        }

        App.httpClient.post(`/code`,{},codes,
            function(response){
                if(response) {
                    self.setCodeVariable(response);
                }
            },
            function(error){
                alert('코드를 불러오는데 실패하였습니다.');
            },
            {
                async:false
            }
        );
    }
}
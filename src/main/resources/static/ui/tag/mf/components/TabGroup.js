import { AbstractComponents } from './AbstractComponents.js';

export class TabGroup extends AbstractComponents {
    render() {
        const id = super.getId();

        const div = document.createElement('div');
        div.className = 'tab-group';

        // header
        const cardHeaderDiv = document.createElement('div');
        cardHeaderDiv.className = 'card-header';

        const nav = document.createElement('ul');
        nav.className = 'nav nav-tabs';
        nav.role = 'tablist';

        for(let tabTag of this.children) {
            if(tabTag.tagName.toLowerCase() != 'mf-tab') {
                continue;
            }

            const navItem = document.createElement('li');
            navItem.className = 'nav-item';

            const navLink = document.createElement('a');
            navLink.className = 'nav-link';
            navLink.id = tabTag.id + '-tab';
            navLink.role = 'tab';
            navLink.href = '#'+tabTag.id;
            navLink.textContent = tabTag.title;

            navLink.setAttribute('data-toggle','pill');
            navLink.setAttribute('aria-controls',tabTag.id);

            if(tabTag.hasAttribute('active')) {
                navLink.className += ' active';
                navLink.setAttribute('aria-selected','true');
            }
            else {
                navLink.setAttribute('aria-selected','false');
            }

            navItem.appendChild(navLink);

            nav.appendChild(navItem);
        }

        cardHeaderDiv.appendChild(nav);

        div.appendChild(cardHeaderDiv);

        // body
        const cardBodyDiv = document.createElement('div');
        cardBodyDiv.className = 'card-body';

        const tabContentDiv = document.createElement('div');
        tabContentDiv.className = 'tab-content';

        while (this.firstChild) tabContentDiv.appendChild(this.firstChild);

        cardBodyDiv.appendChild(tabContentDiv);

        div.appendChild(cardBodyDiv);

        this.replaceWith(div);
    }
}
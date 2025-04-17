let activeTextarea = null;
const treeContainer = document.getElementById('tree');
let allData = []

document.addEventListener('focusin', (e) => {
    if (e.target.tagName === 'TEXTAREA') {
        activeTextarea = e.target;
        const category = e.target.id;

        // отфильтровать и отрисовать дерево под категорию
        const filtered = allData.filter(item => item.category === category);
        const treeData = buildTree(filtered);

        treeContainer.innerHTML = ''; // очистить
        treeContainer.appendChild(renderTree(treeData));
    }
});

// Сборка дерева
function buildTree(flatList) {
    const map = {};
    const roots = [];

    flatList.forEach(item => map[item.id] = { ...item, children: [] });

    flatList.forEach(item => {
        if (item.parent && item.parent.id) {
            const parent = map[item.parent.id];
            if (parent) {
                parent.children.push(map[item.id]);
            }
        } else {
            roots.push(map[item.id]);
        }
    });

    return roots;
}

// Рендер дерева
function renderTree(nodes) {
    const ul = document.createElement('ul');

    nodes.forEach(node => {
        const li = document.createElement('li');
        const span = document.createElement('span');
        span.textContent = `📁 ${node.name}`;
        span.classList.add('folder');

        const childContainer = document.createElement('ul');
        childContainer.classList.add('hidden');

        if (node.values) {
            node.values.forEach(val => {
                const valLi = document.createElement('li');
                valLi.textContent = `📄 ${val.name}`;
                valLi.addEventListener('click', (e) => {
                    e.stopPropagation(); // ✅ остановим всплытие

                    if (activeTextarea) {
                        activeTextarea.value += (activeTextarea.value ?  ' ' : '') + val.name;
                    }
                });
                childContainer.appendChild(valLi);
            });
        }

        if (node.children && node.children.length > 0) {
            const childTree = renderTree(node.children);
            childContainer.appendChild(childTree);
        }

        span.onclick = () => {
            childContainer.classList.toggle('hidden');
        };

        li.appendChild(span);
        li.appendChild(childContainer);
        ul.appendChild(li);
    });

    return ul;
}

// Загрузка JSON (замени путь если берешь с API)
fetch('/med_card/directory')
    .then(res => res.json())
    .then(data => {
        console.log(data)
        allData = data;
    });
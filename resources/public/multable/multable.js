const QUESTIONS_PER_PAGE = 12;

/* ---------- 產題 ---------- */

function shuffle(arr) {
  for (let i = arr.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    const t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }
  return arr;
}

// 每題獨立隨機決定空格位置：75% 空答案，25% 空乘數
function pickBlank() {
  return Math.random() < 0.75 ? 'product' : 'factor';
}

function buildQuestions(from, to, order) {
  if (from > to) {
    const t = from;
    from = to;
    to = t;
  }
  const questions = [];
  for (let a = from; a <= to; a++) {
    for (let b = 1; b <= 9; b++) {
      questions.push({ a: a, b: b, blank: pickBlank() });
    }
  }
  return order === 'rand' ? shuffle(questions) : questions;
}

function paginate(items, size) {
  const pages = [];
  for (let i = 0; i < items.length; i += size) {
    pages.push(items.slice(i, i + size));
  }
  return pages;
}

/* ---------- 渲染 ---------- */

function span(className, text) {
  const el = document.createElement('span');
  el.className = className;
  if (text !== undefined) el.textContent = text;
  return el;
}

// product: a × b = ▢     factor: a × ▢ = a*b
function makeQuestionEl(q, number) {
  const el = document.createElement('div');
  el.className = 'q';
  el.appendChild(span('q-no', number + '.'));

  const eq = span('q-eq');
  eq.appendChild(span('num', q.a));
  eq.appendChild(span('op', '×'));
  if (q.blank === 'product') {
    eq.appendChild(span('num', q.b));
    eq.appendChild(span('op', '='));
    eq.appendChild(span('box'));
  } else {
    eq.appendChild(span('box'));
    eq.appendChild(span('op', '='));
    eq.appendChild(span('num', q.a * q.b));
  }
  el.appendChild(eq);
  return el;
}

function makeSheetEl(page, pageIndex, pageCount, firstNumber) {
  const sheet = document.createElement('div');
  sheet.className = 'sheet';

  const header = span('sheet-header');
  const top = span('sheet-top');
  top.appendChild(span('sheet-title', '乘法練習'));
  top.appendChild(span('sheet-page', '第 ' + (pageIndex + 1) + ' 頁 ／ 共 ' + pageCount + ' 頁'));
  header.appendChild(top);

  const fields = span('sheet-fields');
  fields.appendChild(span('sheet-field', '姓名'));
  fields.appendChild(span('sheet-blank'));
  fields.appendChild(span('sheet-field', '日期'));
  fields.appendChild(span('sheet-blank'));
  header.appendChild(fields);

  sheet.appendChild(header);

  const grid = span('grid');
  page.forEach(function (q, i) {
    grid.appendChild(makeQuestionEl(q, firstNumber + i));
  });
  sheet.appendChild(grid);

  return sheet;
}

/* ---------- 綁定 ---------- */

const fromSel = document.getElementById('from');
const toSel = document.getElementById('to');
const sheetsEl = document.getElementById('sheets');
const hintEl = document.getElementById('hint');

for (let n = 1; n <= 9; n++) {
  const a = document.createElement('option');
  a.value = a.textContent = n;
  fromSel.appendChild(a);
  const b = document.createElement('option');
  b.value = b.textContent = n;
  toSel.appendChild(b);
}
fromSel.value = 2;
toSel.value = 9;

function currentOrder() {
  return document.querySelector('input[name="order"]:checked').value;
}

function render() {
  const questions = buildQuestions(Number(fromSel.value), Number(toSel.value), currentOrder());
  const pages = paginate(questions, QUESTIONS_PER_PAGE);

  sheetsEl.replaceChildren.apply(
    sheetsEl,
    pages.map(function (page, i) {
      return makeSheetEl(page, i, pages.length, i * QUESTIONS_PER_PAGE + 1);
    })
  );

  hintEl.textContent = '共 ' + questions.length + ' 題，' + pages.length + ' 頁（每頁 ' + QUESTIONS_PER_PAGE + ' 題）';
}

document.querySelectorAll('#controls select, #controls input[name="order"]').forEach(function (el) {
  el.addEventListener('change', render);
});
document.getElementById('regen').addEventListener('click', render);
document.getElementById('print').addEventListener('click', function () {
  window.print();
});

render();

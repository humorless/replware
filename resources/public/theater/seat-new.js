const assignments = {};
const reservedSeats = new Set();

function assign(name, rowNum, seatStart, seatEnd) {
  for (let s = seatStart; s <= seatEnd; s++) {
    assignments[`${rowNum}-${s}`] = name;
  }
}

function setReserved(rowNum, seatStart, seatEnd) {
  for (let s = seatStart; s <= seatEnd; s++) {
    reservedSeats.add(`${rowNum}-${s}`);
  }
}

// 設置保留席 (1, 16, 17 排全，2 排 12-15)
// setReserved(1, 1, 8);
// setReserved(2, 12, 15);
// setReserved(16, 1, 15);
// setReserved(17, 1, 14);

// 載入預設劃位名單
// example
// assign('name', row, column_start, column_end)


const rowNames = {
  1:'第一排', 2:'第二排', 3:'第三排', 4:'第四排', 5:'第五排',
  6:'第六排', 7:'第七排', 8:'第八排', 9:'第九排', 10:'第十排',
  11:'第十一排', 12:'第十二排', 13:'第十三排', 14:'第十四排', 15:'第十五排',
  16:'第十六排', 17:'第十七排'
};

const theater = document.getElementById('theater');

function seatToCol(s, isEven) {
  if (s <= 3) return s;
  if (isEven) {
    if (s <= 12) return s + 1;
    return s + 2;
  } else {
    if (s <= 11) return s + 1;
    return s + 3;
  }
}

function makeSeatEl(r, s) {
  const key = `${r}-${s}`;
  const name = assignments[key];
  const isReserved = reservedSeats.has(key);

  const el = document.createElement('div');
  el.className = 'seat';
  if (isReserved) el.classList.add('reserved');
  else if (name) el.classList.add('taken');

  if (isReserved) {
    const resEl = document.createElement('div');
    resEl.className = 'seat-name';
    resEl.textContent = '保留席';
    el.appendChild(resEl);
    el.setAttribute('data-tooltip', `${rowNames[r]} ${s}號：保留席`);
  } else if (name) {
    const nameEl = document.createElement('div');
    nameEl.className = 'seat-name';
    nameEl.textContent = name;
    el.appendChild(nameEl);
    el.setAttribute('data-tooltip', `${rowNames[r]} ${s}號：${name}`);
  }

  const numEl = document.createElement('div');
  numEl.className = 'seat-num';
  numEl.textContent = s;
  el.appendChild(numEl);
  return el;
}

function renderTheater() {
  for (let r = 1; r <= 17; r++) {
    const seatsCount = (r === 1) ? 8 : (r % 2 === 0 ? 15 : 14);
    const isEven = (seatsCount === 15);

    const rowEl = document.createElement('div');
    rowEl.className = 'row';

    const labelLeft = document.createElement('div');
    labelLeft.className = 'row-label';
    labelLeft.textContent = rowNames[r];

    const seatsEl = document.createElement('div');
    seatsEl.className = 'seats';

    if (r === 1) {
      for (let s = 1; s <= 8; s++) {
        const el = makeSeatEl(r, s);
        el.style.gridColumn = s + 4;
        seatsEl.appendChild(el);
      }
    } else {
      const a1 = document.createElement('div'); a1.className = 'aisle'; a1.style.gridColumn = '4'; seatsEl.appendChild(a1);
      const a2 = document.createElement('div'); a2.className = 'aisle'; a2.style.gridColumn = '14'; seatsEl.appendChild(a2);

      if (!isEven) {
        const sp = document.createElement('div');
        sp.style.gridColumn = '13';
        sp.style.visibility = 'hidden';
        seatsEl.appendChild(sp);
      }

      for (let s = 1; s <= seatsCount; s++) {
        const el = makeSeatEl(r, s);
        el.style.gridColumn = seatToCol(s, isEven);
        seatsEl.appendChild(el);
      }
    }

    const labelRight = document.createElement('div');
    labelRight.className = 'row-label';
    labelRight.style.textAlign = 'left';
    labelRight.style.paddingLeft = '10px';
    labelRight.textContent = rowNames[r];

    rowEl.appendChild(labelLeft);
    rowEl.appendChild(seatsEl);
    rowEl.appendChild(labelRight);
    theater.appendChild(rowEl);
  }
}

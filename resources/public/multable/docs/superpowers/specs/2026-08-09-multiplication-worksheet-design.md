# 乘法填充題作業產生器 Design

**Date:** 2026-08-09
**Project:** multable (`resources/public/multable`)

## Summary

一個純前端、無 build step 的網頁工具。家長選好乘法範圍與題目順序，頁面即時產生分頁的乘法填充題作業，按 Ctrl+P 就能印出 A4 作業紙。技術結構比照同目錄的 `theater/`：一個 `index.html` + 一支 `.css` + 一支 `.js`，可直接部署到 GitHub Pages。

## Architecture

三個檔案，無框架、無相依套件、無 build：

```
multable/
  index.html     控制列 + #sheets 容器 + 綁事件的 inline <script>
  multable.css   螢幕樣式 + @media print + @page A4
  multable.js    產題 / 洗牌 / 分頁 / 渲染
```

資料流：

```dot
digraph flow {
  rankdir=LR;
  "控制列 change" -> "readSettings()" -> "buildQuestions()" -> "paginate()" -> "render()" -> "#sheets";
}
```

沒有顯示/隱藏狀態機：控制列永遠在，作業永遠在，`@media print` 時控制列消失。任一控制項變動就整份重畫。

## 控制列 (`.controls`)

| 元件 | 型別 | 值 |
|---|---|---|
| 乘法範圍 起 | `<select id="from">` | 1–9，預設 2 |
| 乘法範圍 迄 | `<select id="to">` | 1–9，預設 9 |
| 題目順序 | `<input type="radio" name="order">` | `seq`（順排，預設）／ `rand`（隨機） |
| 重新產生 | `<button id="regen">` | 重跑一次 render |

- 標籤文字：`乘法範圍：從 [ ] 到 [ ]`、`題目順序：順排／隨機`。
- 只想練一個數字時，起迄選同一個值（例如 從 9 到 9）。
- `from > to` 時內部自動對調，不顯示錯誤。
- 「重新產生」在隨機模式下換一批題序；在順排模式下題序不變，但重洗填空位置。

## 產題邏輯 (`multable.js`)

```
buildQuestions(from, to, order) -> [{a, b, blank}]
  a 走訪 from..to，b 走訪 1..9  →  (to - from + 1) * 9 題
  order === 'seq'  : 依 a 遞增、再依 b 遞增
  order === 'rand' : Fisher–Yates 洗牌
  blank: 每題獨立隨機，Math.random() < 0.75 ? 'product' : 'factor'

paginate(questions, 12) -> [[q...], [q...], ...]
```

- `blank === 'product'` 渲染為 `a × b = ▢`
- `blank === 'factor'` 渲染為 `a × ▢ = a*b`
- 乘號一律用 `×`（U+00D7），符合國小作業慣例。
- 最後一頁不補滿。例如 從 2 到 4 = 27 題 → 3 頁（12 / 12 / 3）。
- 題號跨頁連續編號（1 到 N）。

## 版面 (`multable.css`)

**列印**
- `@page { size: A4; margin: 15mm }` → 內容區 180mm × 267mm
- 每個 `.sheet` 是一張 A4，`page-break-after: always`（最後一張 `:last-child` 取消）
- `@media print`：`.controls { display: none }`、`.sheet` 去掉陰影與外距

**每頁結構**
```
.sheet
  .sheet-header    姓名：____________　日期：____________　　第 N 頁 / 共 M 頁
  .grid            2 欄 × 6 列 grid，共 12 格
    .q             題號 + 算式
```
- `.grid`：`grid-template-columns: 1fr 1fr`，列高平均分配剩餘空間。
- `.q` 算式字級約 28pt，題號約 14pt 置於算式左側。
- 填空格 `.box`：約 18mm × 12mm 的方框（`border: 2px solid`），小孩好寫。

**螢幕**
- 沿用 theater 的色調：米色底 `--bg: #f5f0e8`、襯線標題、`--gold: #8b5e1a`。
- `.sheet` 在螢幕上白底加陰影，模擬紙張；寬 210mm、高 297mm、`padding: 15mm`（`@page margin` 只在列印生效，螢幕上要自己補），視窗窄時單純允許水平捲動，不做縮放。列印時 `padding` 歸零，改由 `@page margin` 負責。
- 字型沿用 Noto Serif TC / Noto Sans TC（Google Fonts CDN），與 theater 一致。

## Error Handling

輸入全部是 `<select>` 與 `radio`，使用者無法輸入非法值，因此沒有錯誤路徑。`from > to` 靜默對調。這與 `theater` 的做法一致。

## 不做的事 (YAGNI)

- 不做答案卷（已確認不需要）
- 不做每頁題數設定（固定 12）
- 不做 localStorage 記憶設定
- 不做加減除法
- 不做 query string 分享連結

## 驗收清單

無測試框架，以手動驗收為準：

1. 從 2 到 2 → 9 題、1 頁，全部是 2 × b。
2. 從 2 到 4 → 27 題、3 頁，分頁為 12 / 12 / 3，題號 1–27 連續。
3. 從 9 到 9 → 9 題、1 頁。
4. 從 9 到 2（起大於迄）→ 與從 2 到 9 相同結果。
5. 隨機模式按兩次「重新產生」→ 題序不同。
6. 順排模式按「重新產生」→ 題序相同，填空位置有變。
7. 兩種填空形式都出現，且 `a × ▢ = a*b` 的答案數字正確。
8. 瀏覽器列印預覽：不含控制列、每張 A4 不溢出、不多出空白頁。

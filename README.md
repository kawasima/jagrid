# jagrid

A CSS framework for Japanese-styled grid sheet.

## 日本向けのグリッドスタイル

Excel方眼紙業務のWeb移行においては、Bootstrapのような12カラムグリッドスタイルでは、
グリッドが足りません。
そこでExcel方眼紙と同じようにレイアウティングできるCSSフレームワークがjagridです。

![sales-report](https://farm8.staticflickr.com/7215/13942407502_b513163fcf.jpg)

## 使い方

```html
<link href="css/jagrid.css" rel="stylesheet"/>
<script src="js/jagrid.js"></script>
```

jagridのスタイルシートとJavascriptファイルを読みこんでください。

## 動作環境

* Chrome
* Firefox
* Edge

## Overview

### シートの作り方

任意の要素に`jagrid`クラスを定義すると、その中を方眼紙として使えるようになります。

```html
<div class="jagrid" style="width: 400px; height: 200px;">
</div>
```

### 狙った位置に要素を書き込む

シートの中では、絶対位置を`data-x`および`data-y`属性を使って指定できます。
ここでは、ピクセル単位ではなく、方眼紙のマス目単位であることに喜びを感じてください。

![hello](https://farm8.staticflickr.com/7207/13965476593_0f84a05a00.jpg)

```html
<div class="jagrid" style="width: 400px; height: 150px;">
 <div data-x="1" data-y="1">
  <p>こんにちは、Excel方眼紙</p>
 </div>
</div>
```

### 表組み

シートの中に、まるでExcel方眼紙のように柔軟に表組みを書くことができます。

![table](https://farm3.staticflickr.com/2902/13942308062_ebc5aa18ef.jpg)

```html
<div class="jagrid" style="width: 400px; height: 150px;">
 <div data-x="1" data-y="1">
  <table>
   <tbody>
    <tr>
     <th class="title" data-width="3">ID</th>
     <th class="title" data-width="5">NAME</th>
    </tr>
    <tr>
     <td>51</td>
     <td>イチロー</td>
    </tr>
   </tbody>
  </table>
 </div>
</div>
```

列幅はこれもマス目の単位であるため、data-width属性を使って指定します。rowspan属性はふつうのtdダグと同じように使用できます。

### リスト

マス目インデントを使った、箇条書きもHTMLのリストでふつうに表現できます。

![list](https://farm8.staticflickr.com/7025/13965476453_a30eddce47.jpg)

```html
<div class="jagrid" style="width: 400px; height: 150px;">
 <div data-x="1" data-y="1">
  <ul>
   <li>りんご</li>
   <li>ばなな</li>
   <li>いちご</li>
  </ul>
 </div>
 <div data-x="8" data-y="1">
  <ol>
   <li>りんご</li>
   <li>ばなな</li>
   <li>いちご</li>
  </ol>
 </div>
</div>
```

## ビルドの仕方

```
% clj -M:run
```

で、cssの下に`jagrid.css`が生成されます。

## 開発モード

```
% clj -M:dev
```

REPLが起動します。`(go)` でサーバーを起動、`(reset)` でリロードします。

## ライセンス

Source Copyright © 2014-2026 kawasima.
Distributed under the Eclipse Public License, the same as Clojure uses.

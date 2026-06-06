# 02 GitFlow 風ブランチ操作

## レッスンのリポジトリ

- 前のレッスン: [git-lesson-01-basic](https://github.com/bp22029/git-lesson-01-basic.git)
- このレッスン: [git-lesson-02-gitflow](https://github.com/bp22029/git-lesson-02-gitflow.git)
- 次のレッスン: [git-lesson-03-conflict](https://github.com/bp22029/git-lesson-03-conflict.git)

## 学習目標

この演習では、`main`、`develop`、`feature` ブランチを使った GitFlow 風の作業を、通常の `git` コマンドだけで体験します。

この演習の主目的は Java の実行ではなく、ブランチを作成し、変更を commit し、別のブランチへ merge する流れを理解することです。Java のコードは、研究活動に近い小さな題材として使います。

終了後、次のことを説明できるようになることを目標にします。

- `main`、`develop`、`feature` の役割を説明できる
- `git switch -c` で新しいブランチを作成できる
- feature ブランチで小さな変更を commit できる
- feature ブランチの変更を `develop` に merge できる
- `git log --oneline --graph --all` で履歴を確認できる

## 前提条件

- 1コマ目の Git / GitHub 基本操作を終えている
- 自分用リポジトリを clone している
- VS Code とターミナルを使える
- Java を実行できると望ましい

Java が実行できない場合でも、`analysis_plan.md` を編集して commit と merge の演習を完了できます。

Java の実行確認:

```bash
javac -version
```

```bash
java -version
```

## 作業場所と clone

このレッスンでは、01 で作成した授業用の親フォルダを使います。

まだこのレッスンのリポジトリを clone していない場合は、授業用の親フォルダで clone します。

```bash
git clone <GitHubリポジトリURL>
```

```bash
cd <リポジトリ名>
```

確認ポイント:

- 授業用の親フォルダの中に、このレッスンのリポジトリフォルダが作成されたか確認します。
- ターミナルが clone したリポジトリの中にいる状態で、手順に進みます。

## このリポジトリで使うファイル

```text
analysis_plan.md
data/measurements.csv
src/SummarizeMeasurements.java
```

## ブランチの考え方

```text
main
  |
  o----------------------o
   \
    develop
      |
      o-----------o
       \         /
        feature/add-summary
```

- `main`: 完成した安定版を置くブランチ
- `develop`: 授業中に作業をまとめるブランチ
- `feature/...`: 1つの変更を試すためのブランチ

## 手順

ここまでは、GitFlow 風のブランチ操作の考え方です。ここからは、説明を読みながら全員で同じ操作を行う基本手順です。

### 1. 現在のブランチを確認する

```bash
git status
```

確認ポイント:

- `On branch main` と表示されるか確認します。
- 変更中のファイルがある場合は、先に commit するか教員に相談します。

### 2. develop ブランチを作成する

```bash
git switch -c develop
```

確認ポイント:

- `Switched to a new branch 'develop'` と表示されるか確認します。

### 3. develop ブランチを GitHub に送る

```bash
git push -u origin develop
```

確認ポイント:

- GitHub の branch 一覧に `develop` が表示されるか確認します。

### 4. feature ブランチを作成する

```bash
git switch -c feature/add-summary
```

```bash
git status
```

確認ポイント:

- `On branch feature/add-summary` と表示されるか確認します。

### 5. 分析計画を確認する

VS Code で `analysis_plan.md` を開き、`今回追加する分析` の欄に次のような内容を追記します。

```text
- Javaプログラムの出力に、研究メモ用の確認観点を追加する。
```

### 6. Java ファイルを編集する

VS Code で `src/SummarizeMeasurements.java` を開き、`System.out.println("測定データの要約");` の近くに次の1行を追加します。

```java
System.out.println("確認観点: 条件ごとの測定値の違いを研究メモに記録する。");
```

確認ポイント:

- 変更は1行だけでも十分です。
- この演習では、大きなプログラムを書くことではなく、Git の流れを確認することを優先します。

### 7. Java を実行して確認する

Java が使える場合は、次を実行します。

```bash
javac src/SummarizeMeasurements.java
```

```bash
java -cp src SummarizeMeasurements
```

確認ポイント:

- 件数、平均値、最小値、最大値がターミナルに表示されるか確認します。
- 追加した「確認観点」の行が表示されるか確認します。

Java が実行できない場合:

`analysis_plan.md` に次の1行を追記して先に進みます。

```text
- Java実行環境は未確認だが、今回はブランチ操作を優先する。
```

### 8. 変更を commit する

```bash
git status
```

Java を編集した場合:

```bash
git add analysis_plan.md src/SummarizeMeasurements.java
```

Java が実行できず、`analysis_plan.md` だけを編集した場合:

```bash
git add analysis_plan.md
```

```bash
git commit -m "要約出力に確認観点を追加"
```

確認ポイント:

- `git log --oneline` で commit が増えているか確認します。

```bash
git log --oneline
```

### 9. develop に戻って merge する

```bash
git switch develop
```

```bash
git merge feature/add-summary
```

確認ポイント:

- `Fast-forward` または merge 完了の表示が出るか確認します。
- `Fast-forward` と表示されてもエラーではありません。`develop` が feature ブランチの変更をそのまま前に進められる状態だった、という意味です。
- `analysis_plan.md` や `src/SummarizeMeasurements.java` に feature ブランチの変更が入っているか確認します。

### 10. merge 後の履歴を確認する

```bash
git log --oneline --graph --all
```

確認ポイント:

- `feature/add-summary` で作成した commit が履歴に表示されるか確認します。
- `develop` が、その commit を含んでいるか確認します。
- 表示を終了できない場合は、`q` キーを押します。

### 11. develop を GitHub に push する

```bash
git push
```

確認ポイント:

- GitHub の `develop` ブランチで変更を確認します。

## 補足: feature ブランチを GitHub に push する

3コマ目の Pull Request 演習では、feature ブランチを GitHub に push してから Pull Request を作成します。

今回の演習でも、feature ブランチを GitHub に送る場合は次のように実行できます。

```bash
git push -u origin feature/add-summary
```

## 発展: merge commit を明示的に作る

この授業では通常の `git merge feature/add-summary` を使います。実行結果として `Fast-forward` になる場合がありますが、それは自然な動作です。

履歴に「feature ブランチを merge した」という commit を明示的に残したい場合は、発展として次のコマンドがあります。

```bash
git merge --no-ff feature/add-summary -m "Merge feature/add-summary into develop"
```

注意:

- 初学者演習では必須ではありません。
- `--no-ff` は fast-forward できる場合でも merge commit を作る指定です。
- 授業中は、教員の指示がある場合だけ使ってください。

## 演習課題

ここからは、上の説明付き基本手順を参考にして、自分でブランチを切って小さな変更を行います。

課題:

- `develop` から新しい feature ブランチを作る
- `analysis_plan.md` に、今後やることの Todo を2つ追記する
- 余裕があれば、Todo のうち1つについて「なぜ必要か」を1文で追記する
- 変更を commit する
- `develop` に merge する
- `git log --oneline --graph --all` で履歴を確認する

ブランチ名の例:

```bash
git switch develop
```

```bash
git switch -c feature/add-todo
```

`analysis_plan.md` に追加する内容の例:

```text
## 今後やること

- 興味のある研究キーワードを3つ書き出す。
- 先行研究を1本探して、分かったことをメモする。
- 必要な理由: 研究テーマを決める前に、どの分野で何が議論されているかを知るため。
```

commit の例:

```bash
git status
```

```bash
git add analysis_plan.md
```

```bash
git commit -m "今後やることのTodoを追加"
```

merge の例:

```bash
git switch develop
```

```bash
git merge feature/add-todo
```

履歴確認:

```bash
git log --oneline --graph --all
```

この課題の主目的:

- 指示された1つの例だけでなく、自分で branch 名、変更内容、commit の流れを考えること
- 研究内容を深く分析することではなく、軽い Todo 追加を通して Git の流れを体感すること

## 期待される結果

- `develop` ブランチが作成されている
- `feature/add-summary` ブランチで作業した commit がある
- feature ブランチの変更が `develop` に merge されている
- `git log --oneline --graph --all` で履歴を確認できる
- GitHub 上でも `develop` ブランチの内容を確認できる

## よくあるエラー

### `fatal: a branch named 'develop' already exists`

原因:

すでに `develop` ブランチがあります。

対応:

```bash
git switch develop
```

### `error: Your local changes to the following files would be overwritten`

原因:

commit していない変更がある状態でブランチを切り替えようとしています。

対応:

```bash
git status
```

変更を確認し、必要なら commit します。不要な変更を消す操作は教員に確認してから行ってください。

### Java が実行できない

確認すること:

```bash
javac -version
```

```bash
java -version
```

どちらかが使えない場合でも、この演習では先に進めます。`analysis_plan.md` に次の行を追記して commit と merge の練習を続けます。

```text
- Java実行環境は未確認だが、今回はブランチ操作を優先する。
```

## 提出物

- GitHub 上の `develop` ブランチ URL
- `feature/add-summary` で作成した commit の URL
- `analysis_plan.md` の変更内容
- 授業中の作業記録

## 振り返り質問

- `main` と `develop` はどのように使い分けるとよいですか。
- feature ブランチを使う利点は何ですか。
- merge すると、どのブランチに変更が取り込まれますか。
- `Fast-forward` と表示された場合、それは何を意味しますか。
- Java が実行できない場合でも、この演習で達成すべき Git の操作は何ですか。

## 提出前チェックリスト

- [ ] `develop` ブランチを作成した
- [ ] `feature/add-summary` ブランチを作成した
- [ ] `analysis_plan.md` を編集した
- [ ] Java ファイルを編集した、または Java が未確認であることを `analysis_plan.md` に追記した
- [ ] feature ブランチで commit した
- [ ] `develop` に merge した
- [ ] `git log --oneline --graph --all` で履歴を確認した
- [ ] `develop` を GitHub に push した
- [ ] 作業記録を書いた

# 历史上的今天 / History Today

[![Language: Kotlin](https://img.shields.io/badge/language-Kotlin-purple.svg)](https://kotlinlang.org)
[![Platform: Android](https://img.shields.io/badge/platform-Android-brightgreen.svg)](https://android.com)
[![Architecture: MVP](https://img.shields.io/badge/architecture-MVP-blue.svg)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)
[![API: Juhe](https://img.shields.io/badge/API-Juhe-orange.svg)](https://juhe.cn)

---

## 📖 项目简介 / Overview

**历史上的今天** 是一款 Android 应用，展示传统黄历信息以及"历史上的今天"历史事件。

**History Today** is an Android application that displays traditional Chinese calendar (Lao Huang Li) information and historical events that happened "on this day in history."

---

## ✨ 功能特性 / Features

### 🏮 老黄历 / Lao Huang Li (Traditional Chinese Calendar)

| 功能 | 说明 |
|------|------|
| 默认显示当天黄历 | 农历、五行、冲煞、宜忌等信息 |
| 日期选择 | 通过日历对话框选择任意日期查看 |
| 时辰信息 | 获取当天各时辰的吉凶宜忌 |

| Feature | Description |
|---------|-------------|
| Default today view | Lunar date, five elements, clash info, auspicious/inauspicious |
| Date picker | Select any date via calendar dialog |
| Hour info | Hour-level auspicious/inauspicious data |

### 📜 历史上的今天 / Today in History

| 功能 | 说明 |
|------|------|
| 首页概览 | 默认展示前 5 条历史事件 |
| 完整列表 | 点击底部"查看更多"跳转完整列表页 |
| 事件详情 | 点击事件条目查看详情（标题、内容、图片） |
| 分享功能 | 在详情页可分享事件内容 |

| Feature | Description |
|---------|-------------|
| Home summary | Top 5 events on main page |
| Full list | Tap "view more" footer for full list |
| Event detail | Tap item → title, content, image |
| Share | Share event details from detail page |

---

## 🏗 技术栈 / Tech Stack

| 类别 / Category | 技术 / Technology | 版本 / Version |
|----------------|-------------------|----------------|
| 语言 / Language | **Kotlin** (100%) | 2.2.20 |
| 构建系统 / Build | Gradle + AGP | 8.9 / 8.1.2 |
| 最低 SDK / Min SDK | Android | 24 |
| 目标 SDK / Target SDK | Android | 34 |
| 架构 / Architecture | **MVP** (Model-View-Presenter) | — |
| 网络 / Networking | **Retrofit 2** + **OkHttp 4** | 2.9.0 / 4.11.0 |
| 响应式 / Reactive | **RxJava 3** + **RxAndroid 3** | 3.1.8 / 3.0.2 |
| JSON 解析 / JSON | **Gson** | 2.10.1 |
| 图片加载 / Images | **Picasso** | 2.8 |
| UI | AndroidX + CardView + ConstraintLayout | — |

---

## 🧱 项目结构 / Project Structure

```
app/src/main/java/com/animee/todayhistory/
├── bean/                          # 数据模型 / Data models
│   ├── HistoryBean.kt
│   ├── HistoryDescBean.kt
│   ├── LaoHuangliBean.kt
│   └── LaoHuangliHoursBean.kt
├── network/                       # 网络层 / Network layer
│   ├── ApiService.kt              # Retrofit API 接口
│   └── RetrofitClient.kt          # Retrofit 单例 + OkHttp 配置
├── ui/                            # UI 层 / UI layer
│   ├── base/
│   │   ├── UniteApp.kt            # Application 类
│   │   └── ContentURL.kt          # URL 工具类
│   ├── HistoryActivity.kt         # 历史事件列表页
│   ├── HistoryAdapter.kt          # ListView 适配器
│   ├── HistoryDescActivity.kt     # 历史事件详情页
│   ├── HistoryDescContact.kt      # 详情页 MVP Contract
│   ├── HistoryDescModel.kt        # 详情页 Model
│   └── HistoryDescPresenter.kt    # 详情页 Presenter
├── MainActivity.kt                # 主界面 / Main screen
├── MainContract.kt                # 主页 MVP Contract
├── MainModel.kt                   # 主页 Model
└── MainPresenter.kt               # 主页 Presenter
```

---

## 🏛 架构 / Architecture

项目采用 **MVP** 模式，通过 Contract 接口定义 View 与 Presenter 的契约：

```
┌──────────────────┐    回调/通知     ┌────────────────┐
│   View (Activity) │◄────────────────│  Presenter     │
│   — 界面渲染       │                │  — 业务逻辑     │
│   — 用户交互       │                │  — 网络请求     │
└──────────────────┘                 └───────┬────────┘
                                             │
                                    ┌────────▼────────┐
                                    │  Model / API    │
                                    │  — 数据处理      │
                                    │  — Retrofit     │
                                    └─────────────────┘
```

**关键设计 / Key Design Decisions:**

- **Contract 接口**分离 View 职责和 Presenter 职责，便于单元测试
- **Presenter** 持有 `CompositeDisposable` 管理 RxJava 订阅，避免内存泄漏
- **Model 层**使用 `object` 单例（Kotlin 原生支持），替代 Java DCL 模式
- **网络请求**统一通过 Retrofit + RxJava，保证异步不阻塞主线程

---

## 📡 API 接口 / APIs

所有接口由 [聚合数据 (Juhe)](https://juhe.cn) 提供。

| 接口 / API | 端点 / Endpoint | 参数 | 说明 |
|-----------|----------------|------|------|
| 老黄历 | `GET /laohuangli/d` | `date` (yyyy-MM-dd), `key` | 每日黄历信息 |
| 时辰信息 | `GET /laohuangli/h` | `date` (yyyy-MM-dd), `key` | 时辰吉凶 |
| 历史上的今天 | `GET /todayOnhistory/queryEvent` | `date` (M/d), `key` | 当日历史事件列表 |
| 事件详情 | `GET /todayOnhistory/queryDetail` | `e_id`, `key` | 事件详细内容 |

> **安全说明 / Security Note:** 由于 API 使用 HTTP 而非 HTTPS，App 通过 `network_security_config.xml` 允许对 `v.juhe.cn` 和 `api.juheapi.com` 的明文传输。

---

## 🛠 构建与运行 / Build & Run

### 前置要求 / Prerequisites

| 工具 | 版本要求 |
|------|---------|
| Android Studio | 4.0+ |
| Android SDK | 24+ |
| Gradle | 7.0+ |
| JDK | 8+ |

### 构建 / Build

```bash
# 克隆 / Clone
git clone <repo-url>

# 打开 Android Studio → Open Project → 选择根目录

# 或命令行构建 / Or build via command line
./gradlew assembleDebug
```

### 安装 / Install

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 🔄 最新变更 / Changelog

### v1.1 (2026-05)

#### 100% Kotlin 迁移 / Full Kotlin Migration

| 变更项 | 说明 |
|--------|------|
| Java → Kotlin | 16 个 Java 文件全部转为 Kotlin，代码量从 ~1,331 行减至 ~811 行（-39%） |
| Bean 层 | Java POJO → Kotlin `data class`，样板代码减少 84% |
| 单例模式 | Java DCL 双重检查锁 → Kotlin `object` 声明 |
| 空安全 | 所有 API 返回字段改为可空类型 `String?`，消除潜在 NPE |
| 构建配置 | 无需修改（Kotlin 2.2.20 插件已内置） |

**迁移明细 / Migration Details:**

| 层 / Layer | 文件数 | 行数缩减 |
|-----------|--------|---------|
| Bean (数据模型) | 4 | 474 → 74 (**-84%**) |
| Network (网络) | 2 | 104 → 84 (**-19%**) |
| Contract + Base | 3 | 54 → 54 (±0%) |
| Model (数据层) | 2 | 85 → 59 (**-31%**) |
| Presenter (业务层) | 2 | 167 → 113 (**-32%**) |
| Activity (界面层) | 3 | 449 → 343 (**-24%**) |
| **总计 / Total** | **16** | **1,331 → 727 (-45%)** |

#### Bug 修复 / Bug Fixes

| Bug | 文件 | 修复 |
|-----|------|------|
| 分享按钮不可点击 | `HistoryDescActivity` | `onClick` 中 `desc_back_iv` 判断两次 → 改为分别判断 `desc_back_iv` 和 `desc_share_iv` |
| 冗余 `runOnUiThread` | `MainActivity` | 移除全部 6 处 `runOnUiThread`（RxJava 已确保主线程回调） |

#### 遗留问题 / Known Issues

- [ ] API Key 硬编码在 Presenter 中，建议移至 `BuildConfig` 或本地配置
- [ ] 所有 API 请求使用 HTTP 而非 HTTPS，存在中间人攻击风险
- [ ] 无本地缓存，每次启动均发起网络请求
- [ ] 详细页暂无加载状态指示器

---

## 📄 许可证 / License

该项目基于 MIT 许可证开源。 / This project is open source under the MIT License.

---

## 🙏 致谢 / Acknowledgments

- 数据由 **[聚合数据 (Juhe)](https://juhe.cn)** 提供
- 使用 [OkHttp](https://square.github.io/okhttp/) 和 [Retrofit](https://square.github.io/retrofit/) 处理网络请求
- 使用 [Picasso](https://square.github.io/picasso/) 加载图片
- 使用 [RxJava](https://github.com/ReactiveX/RxJava) 处理异步操作

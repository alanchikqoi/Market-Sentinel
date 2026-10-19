# 🏢 MarketSentinel (MVP)

Analytical Telegram bot for automated real estate monitoring and real-time evaluation.

## 🎯 Project Overview
Finding hot and undervalued real estate properties requires continuous monitoring. A human cannot physically refresh search results every minute. MarketSentinel automates this process: it regularly parses fresh listings, extracts raw data, calculates the unit economics (price per square meter), and delivers only new listings with a ready-made business verdict directly to Telegram.

## 🥊 Key Features
* **Targeted Parsing:** Automated data extraction (price, area) from real estate platforms (implemented for Krisha.kz). Runs as a background thread using `ScheduledExecutorService`.
* **Automated Analytics:** Dynamic calculation of the price per square meter with actionable recommendations (🔥 BUY / ❌ SKIP) based on predefined market thresholds.
* **Anti-Spam Filter (Execution Order):** Built-in memory cache based on `HashSet` eliminates duplicate alerts, ensuring the bot never sends the same listing twice.
* **Telegram Integration:** Instant delivery of analysis results directly to the user's messenger via Long Polling.

## 🛠 Tech Stack
* **Core:** Java, Object-Oriented Programming principles (Interfaces, Encapsulation).
* **HTML Parsing:** `Jsoup` (handling basic anti-bot protection, DOM tree manipulation).
* **API Integration:** `TelegramBots API`.
* **Architectural Approach:** Application of the Pareto principle (80/20) — focused on a narrow, specialized domain (real estate) rather than building a monolithic, universal parsing tool.

## 🚀 Quick Start
1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
# bilve-tools

## 如何构建

1. Clone 项目到本地

    ```shell
    git clone git@github.com:Dengzixu/bilve-tools.git
    cd bilve-tools
    # 初始化子模块
    git submodule update --init --recursive
    ```
2. 修改配置

    * 修改 `config/application-live.example.yml` 文件名为 `config/application-live.example.yml`
    * 修改 `config/application-live.yml` 中的配置项
    * 修改 `net.dengzixu.bilvetools.config.ApplicationConfig` 中的配置

3. 构建项目
   * Windows
   ```shell
   .\gradlew build
   ```
   
   * Linux
   ```shell
   ./gradlew build
   ```
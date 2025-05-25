import os
import subprocess

def run_commands():
    # 第一条命令：切换目录
    target_dir = r"E:\GPT-Sovits WebUI\GPT-SoVITS-beta\GPT-SoVITS-beta0706"
    try:
        os.chdir(target_dir)
        print(f"成功切换到目录: {target_dir}")
    except Exception as e:
        print(f"切换目录失败: {e}")
        return

    # 第二条命令：运行python程序
    python_exe = r"runtime\python.exe"
    script_name = "api.py"
    try:
        subprocess.run([python_exe, script_name], check=True)
        print("api.py 运行完成")
    except subprocess.CalledProcessError as e:
        print(f"运行api.py时出错: {e}")
    except Exception as e:
        print(f"发生未知错误: {e}")

if __name__ == "__main__":
    run_commands()
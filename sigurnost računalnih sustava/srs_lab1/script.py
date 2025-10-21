import subprocess

def compile_java(java_file):
    subprocess.run(['javac', '-d', 'src', '-cp', 'src', java_file])

def execute_java(java_class, args):
    print("java PswrdMng ", args)
    subprocess.run(['java', '-cp', 'src', java_class, *args])

if __name__ == "__main__":
    compile_java('src/PswrdMng.java')
    compile_java('src/CryptoManager.java')
    compile_java('src/FileManager.java')
    print("\n")

    execute_java('PswrdMng', ['init', 'mAsterPasswrd'])

    print("\n")

    execute_java('PswrdMng', ['put', 'mAsterPasswrd', 'www.fer.hr', 'neprobojnaSifra'])

    print("\n")

    execute_java('PswrdMng', ['get', 'mAsterPasswrd', 'www.fer.hr'])

    print("\n")

    execute_java('PswrdMng', ['get', 'wrongPasswrd', 'www.fer.hr'])

    print("\n")
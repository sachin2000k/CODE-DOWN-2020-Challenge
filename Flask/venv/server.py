from flask import Flask
app = Flask(__name__)

@app.route("/",methods=['GET','POST'])
def hello():
    return "Flask and android connected"


app.run(host="0.0.0.0",port=5000,debug=True)   

from gensim.models import KeyedVectors
from sklearn.metrics.pairwise import cosine_similarity
from flask import Flask, request, jsonify
import difflib
from unidecode import unidecode

app = Flask(__name__)

model = KeyedVectors.load_word2vec_format("C:\\Users\\etudiant\\python\\modele.bin", binary=True, unicode_errors="ignore")

def motSimilaire(s):
    return model.most_similar(s)

def motSimilaireN(s, N):
    L = motSimilaire(s)
    L_triee = sorted(L, key=lambda x: abs(1 - x[1]))
    return L_triee[:N]

def motComparaison(s1, s2):
    if s1 in model and s2 in model:
        v1 = model[s1]
        v2 = model[s2]
        return cosine_similarity([v1], [v2])[0][0]
    else:
        return None


@app.route('/getIndice', methods=['GET'])
def getIndice():
    s = request.args.get('s')
    L = motSimilaire(s)
    L_triee = sorted(L, key=lambda x: abs(1 - x[1]))
    
    # Récupérer la longueur de s
    s_length = len(s)
    
    # Calculer la longueur divisée par 2 en valeur absolue
    half_length = abs(s_length // 2)
    
    # Filtrer la liste triée pour ne contenir que des mots dont la racine n'a pas plus de la moitié des lettres en commun
    L_triee = [word for word, score in L_triee if difflib.SequenceMatcher(None, s, word).ratio() <= 0.5]
    
    # Vous pouvez renvoyer la liste filtrée dans le format que vous souhaitez
    return jsonify({'result': unidecode(L_triee[0])})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)


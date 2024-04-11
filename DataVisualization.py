import numpy as np
import psycopg2
from psycopg2 import Error
import pandas
import geopandas as geo
import geodatasets
import shapely
from shapely.geometry import Point
import matplotlib.pyplot as plt
from scipy import ndimage
import seaborn as sns

true = True
false = False

def start():
    locais = conexao("Select * from local")
    exibirLocais(locais)
    dados = conexao("select local_latitude, local_longitude, count(*) as contagem from dados group by local_latitude, local_longitude order by contagem desc limit 10000")
    locaisMaisFrequentados(dados)
    chicagodata = conexao('select *,count(*) as contagem from (select local_latitude, local_longitude from dados where local_latitude > 41.65 and local_latitude < 42.02 '+
 	'and local_longitude > - 87.85 and local_longitude < - 87.55) ' +
    'group by local_latitude,local_longitude order by contagem desc limit 100')
    chicago(chicagodata)
    chicagoJson(chicagodata)
    dados = conexao("select local_latitude, local_longitude, count(*) as contagem from dados group by local_latitude, local_longitude order by contagem desc limit 100")
    top100SpotsonWorld(dados)

def exibirLocais(locais):
    pontos = {'cordenadas':[]}
    for row in locais:
        pontos["cordenadas"].append(Point([float(row[1]),float(row[0])]))
    gf = geo.GeoDataFrame(pontos).set_geometry("cordenadas")
    world = geo.read_file(geodatasets.get_path('naturalearth.land'))
    print(world.plot(color="white", edgecolor="black"))
    ax = world.plot(color="white", edgecolor="black")
    print(gf.plot(ax=ax,color='red',markersize=0.5))

def locaisMaisFrequentados(dados):
    pontos = {'cordenadas':[], 'contagem':[]}
    for row in dados:
        pontos["cordenadas"].append(Point([float(row[1]),float(row[0])]))
        pontos["contagem"].append(row[2])
    gf = geo.GeoDataFrame(pontos).set_geometry("cordenadas")
    world = geo.read_file(geodatasets.get_path('naturalearth.land'))
    ax = world.plot(color="white", edgecolor="black")
    gf.plot(ax=ax,cmap='Dark2',scheme='quantiles',legend=true,column='contagem',markersize=0.5)

def chicagoJson(dados):
    pontos = {'geometry': [], 'contagem': []}  # Changed 'cordenadas' to 'geometry' to match GeoPandas convention
    for row in dados:
        pontos["geometry"].append(Point(float(row[1]), float(row[0])))  # Fixed indexing of latitude and longitude
        pontos["contagem"].append(row[2])
    gf = geo.GeoDataFrame(pontos, crs="EPSG:4326")
    gf.to_file('C:\\Users\\Mathe\\Desktop\\Bagunça\\vsprojects\\smalldata\\python\\chicago_data.geojson', driver='GeoJSON')

def top100SpotsonWorld(dados):
    pontos = {'geometry': [], 'contagem': []}  # Changed 'cordenadas' to 'geometry' to match GeoPandas convention
    for row in dados:
        pontos["geometry"].append(Point(float(row[1]), float(row[0])))  # Fixed indexing of latitude and longitude
        pontos["contagem"].append(row[2])
    gf = geo.GeoDataFrame(pontos, crs="EPSG:4326")
    gf.to_file('C:\\Users\\Mathe\\Desktop\\Bagunça\\vsprojects\\smalldata\\python\\popularSpots.geojson', driver='GeoJSON')

def chicago(dados):
    chicago = geo.read_file(geodatasets.get_path('geoda.chicago_commpop'))
    pontos = {'cordenadas':[], 'contagem':[]}
    for row in dados:
        pontos["cordenadas"].append(Point([float(row[1]),float(row[0])]))
        pontos["contagem"].append(row[2])
    gf = geo.GeoDataFrame(pontos).set_geometry("cordenadas")
    ax = chicago.plot(color='white',edgecolor='black')
    gf.plot(ax=ax,cmap='jet',scheme='quantiles',legend=true,column='contagem',markersize=2)

def conexao(query):
    try:
        con = psycopg2.connect(user="postgres",password="acwr",host="localhost",port="5432",database="smalldata")
        cursor = con.cursor()
        cursor.execute(query)
        registros = cursor.fetchall()

    except (Exception, Error) as error:
        print(error)
    finally:
        if con is not None:
            cursor.close()    
            con.close()
        print("Conexão com o PostgreSQL fechada.")
        return registros

start()
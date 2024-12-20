#PROIECT RECUPERARE IGESCU FLAVIUS 1221B 19.12.2024
def convert_length(value, from_unit, to_unit):
    length_units = {
        'meters': 1,
        'kilometers': 1000,
        'centimeters': 0.01,
        'millimeters': 0.001,
        'miles': 1609.34,
        'yards': 0.9144,
        'feet': 0.3048,
        'inches': 0.0254
    }
    return value * length_units[from_unit] / length_units[to_unit]

def convert_weight(value, from_unit, to_unit):
    weight_units = {
        'kilograms': 1,
        'grams': 0.001,
        'milligrams': 0.000001,
        'pounds': 0.453592,
        'ounces': 0.0283495,
        'tons': 1000  
    }
    return value * weight_units[from_unit] / weight_units[to_unit]

def convert_temperature(value, from_unit, to_unit):
    if from_unit == 'celsius' and to_unit == 'fahrenheit':
        return (value * 9/5) + 32
    elif from_unit == 'fahrenheit' and to_unit == 'celsius':
        return (value - 32) * 5/9
    elif from_unit == 'celsius' and to_unit == 'kelvin':
        return value + 273.15
    elif from_unit == 'kelvin' and to_unit == 'celsius':
        return value - 273.15
    elif from_unit == 'fahrenheit' and to_unit == 'kelvin':
        return (value - 32) * 5/9 + 273.15
    elif from_unit == 'kelvin' and to_unit == 'fahrenheit':
        return (value - 273.15) * 9/5 + 32
    else:
        return value

def main():
    print("Convertor de unități")
    print("1. Lungime")
    print("2. Greutate")
    print("3. Temperatură")
    choice = int(input("Alege tipul de conversie (1/2/3): "))

    if choice == 1:
        value = float(input("Introdu valoarea: "))
        from_unit = input("Introdu unitatea de la care se face conversia: ").lower()
        to_unit = input("Introdu unitatea la care se face conversia: ").lower()
        result = convert_length(value, from_unit, to_unit)
    elif choice == 2:
        value = float(input("Introdu valoarea: "))
        from_unit = input("Introdu unitatea de la care se face conversia: ").lower()
        to_unit = input("Introdu unitatea la care se face conversia: ").lower()
        result = convert_weight(value, from_unit, to_unit)
    elif choice == 3:
        value = float(input("Introdu valoarea: "))
        from_unit = input("Introdu unitatea de la care se face conversia: ").lower()
        to_unit = input("Introdu unitatea la care se face conversia: ").lower()
        result = convert_temperature(value, from_unit, to_unit)
    else:
        print("Alegere invalidă")
        return

    print(f"Rezultatul conversiei: {result} {to_unit}")

if __name__ == "__main__":
    main()

const convertIpv6ToMac = ipv6 => {
  const macParts = [];
  let ipv6Parts = ipv6.split(':');
  ipv6Parts.shift();
  ipv6Parts.shift();

  for (const i in ipv6Parts) {
    while (ipv6Parts[i].length < 4) {
      ipv6Parts[i] = '0' + ipv6Parts[i];
    }
    macParts.push(ipv6Parts[i].substring(0, 2));
    macParts.push(ipv6Parts[i].substring(2, 4));
  }

  // eslint-disable-next-line no-bitwise
  const inte = parseInt(macParts[0], 16) ^ 2;
  macParts[0] = inte.toString(16);
  delete macParts[4];
  delete macParts[3];
  macParts.splice(4, 1);
  macParts.splice(3, 1);
  let macAdress = '';
  for (let index = 0; index < macParts.length; index++) {
    const element = macParts[index];
    macAdress = macAdress.concat(element);
    if (!(index === 5)) {
      macAdress = macAdress.concat(':');
    }
  }
  return macAdress;
};

export default convertIpv6ToMac;
